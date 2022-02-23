package gohome;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PhpMain {

	public static void main(String[] args) {

		List<String> ls = new ArrayList<String>();
		char alpha;
		// 읽어 올 파일
		File file = new File("/home/findPhpList.txt");
		// 데이터를 쓸 파일
		File file2 = new File("/home/findPhp.txt");

		Path dirPath = Paths.get("/home/");

		List<Path> result;
		Stream<Path> walk;
		//실행할 스크립트
		String cmd = "/root/mysql_check.sh";

		//파일이 존재하지 않으면
		if (!file.exists()) {
			try {
				//파일 생성
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// List에 a~z까지의 문자열 저장
		for (alpha = 'a'; alpha <= 'z'; alpha++) {
			ls.add(String.valueOf(alpha));
		}

		try {
			//dirPath의 최하위단의 파일까지 가져옴
			walk = Files.walk(dirPath);
			//walk를 list화
			result = walk.collect(Collectors.toList());

			// "C:\\Test\\findPhpList.txt"에 쓸 writer
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));

			for (Path path : result) {
				bw.write(path + "\n");
			}

			bw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			// 파일 읽어올 Reader
			BufferedReader br = new BufferedReader(new FileReader(file));
			// 파일을 쓸 Writer
			BufferedWriter bw = new BufferedWriter(new FileWriter(file2));
			String line = null;

			// Reader를 통해 한줄 씩 읽어오고 다음 줄이 없으면 종료
			while ((line = br.readLine()) != null) {

				System.out.println(line);

				//line을 '/'단위로 분리
				String[] splitLine = line.split("\\/");
				// '/'를 기준으로 가장 마지막 배열 요소를 찾기위한 인덱스
				int lastIndex = splitLine.length - 1;

				// a~z까지 담겨있는 List를 탐색한다.
				for (int i = 0; i < ls.size(); i++) {
					// .php라는 문자열을 포함하고, (a~z)로 문자열이 시작되고, 길이가 8 미만인 문자열이면
					if (splitLine[lastIndex].contains(".php") && splitLine[lastIndex].startsWith(ls.get(i))
							&& splitLine[lastIndex].length() < 8) {
						// 길이가 5이면 한글자로 된 파일명이므로 그냥 writer를 통해 txt파일에 씀 ex)a.php
						if (splitLine[lastIndex].length() == 5) {
							bw.write(line + "\n");
							System.out.println(line);
							// 길이가 6이면 첫번째 글자와 두번째 글자가 같은지 확인하여 같으면 writer를 통해 txt파일에 씀 ex) aa.php
						} else if (splitLine[lastIndex].length() == 6) {
							if (splitLine[lastIndex].charAt(0) == splitLine[lastIndex].charAt(1)) {
								bw.write(line + "\n");
								System.out.println(line);
							}
							// 길이가 7이면 첫번째 글자와 두번째 글자가 같은지 확인하고 두번째, 세번째도 비교해서 같으면 writer를 통해 txt파일에 씀 ex)
							// aaa.php
						} else {
							if (splitLine[lastIndex].charAt(0) == splitLine[lastIndex].charAt(1)
									&& splitLine[lastIndex].charAt(1) == splitLine[lastIndex].charAt(2)) {
								bw.write(line + "\n");
								System.out.println(line);
							}
						}
					}
				}
			}

			shellCmd(cmd);
			
			br.close();
			bw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void shellCmd(String command) {
		Runtime runtime = Runtime.getRuntime();
		Process process;
		
		try {
			process = runtime.exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

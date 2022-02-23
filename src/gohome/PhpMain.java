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
		// �о� �� ����
		File file = new File("/home/findPhpList.txt");
		// �����͸� �� ����
		File file2 = new File("/home/findPhp.txt");

		Path dirPath = Paths.get("/home/");

		List<Path> result;
		Stream<Path> walk;
		//������ ��ũ��Ʈ
		String cmd = "/root/mysql_check.sh";

		//������ �������� ������
		if (!file.exists()) {
			try {
				//���� ����
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// List�� a~z������ ���ڿ� ����
		for (alpha = 'a'; alpha <= 'z'; alpha++) {
			ls.add(String.valueOf(alpha));
		}

		try {
			//dirPath�� ���������� ���ϱ��� ������
			walk = Files.walk(dirPath);
			//walk�� listȭ
			result = walk.collect(Collectors.toList());

			// "C:\\Test\\findPhpList.txt"�� �� writer
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));

			for (Path path : result) {
				bw.write(path + "\n");
			}

			bw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			// ���� �о�� Reader
			BufferedReader br = new BufferedReader(new FileReader(file));
			// ������ �� Writer
			BufferedWriter bw = new BufferedWriter(new FileWriter(file2));
			String line = null;

			// Reader�� ���� ���� �� �о���� ���� ���� ������ ����
			while ((line = br.readLine()) != null) {

				System.out.println(line);

				//line�� '/'������ �и�
				String[] splitLine = line.split("\\/");
				// '/'�� �������� ���� ������ �迭 ��Ҹ� ã������ �ε���
				int lastIndex = splitLine.length - 1;

				// a~z���� ����ִ� List�� Ž���Ѵ�.
				for (int i = 0; i < ls.size(); i++) {
					// .php��� ���ڿ��� �����ϰ�, (a~z)�� ���ڿ��� ���۵ǰ�, ���̰� 8 �̸��� ���ڿ��̸�
					if (splitLine[lastIndex].contains(".php") && splitLine[lastIndex].startsWith(ls.get(i))
							&& splitLine[lastIndex].length() < 8) {
						// ���̰� 5�̸� �ѱ��ڷ� �� ���ϸ��̹Ƿ� �׳� writer�� ���� txt���Ͽ� �� ex)a.php
						if (splitLine[lastIndex].length() == 5) {
							bw.write(line + "\n");
							System.out.println(line);
							// ���̰� 6�̸� ù��° ���ڿ� �ι�° ���ڰ� ������ Ȯ���Ͽ� ������ writer�� ���� txt���Ͽ� �� ex) aa.php
						} else if (splitLine[lastIndex].length() == 6) {
							if (splitLine[lastIndex].charAt(0) == splitLine[lastIndex].charAt(1)) {
								bw.write(line + "\n");
								System.out.println(line);
							}
							// ���̰� 7�̸� ù��° ���ڿ� �ι�° ���ڰ� ������ Ȯ���ϰ� �ι�°, ����°�� ���ؼ� ������ writer�� ���� txt���Ͽ� �� ex)
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

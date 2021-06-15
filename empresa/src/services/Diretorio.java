package services;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Diretorio {

	/**
	 * Extrai o nome do arquivo a partir do path
	 * @param diretorio - o caminho completo contendo o nome do arquivo
	 * @return - o nome do arquivo
	 */
	public static String getFileNameFromPath(String diretorio) {
		Path path = Paths.get(diretorio);
		return path.getFileName().toString();
	}


	/**
	 * Cria um diret�rio 
	 * @param dir - o caminho completo do diret�rio
	 * @return - true em caso de sucesso na cria��o do diret�rio ou false caso contr�rio
	 */
	public static boolean createDir(String dir) {
		try {
			File diretorio = new File(dir); 
			if (!diretorio.exists()) { 
				diretorio.mkdirs(); 
			}
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}

}

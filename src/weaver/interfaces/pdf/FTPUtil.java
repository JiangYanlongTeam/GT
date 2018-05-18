package weaver.interfaces.pdf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import weaver.general.BaseBean;
import weaver.general.xziputil.commons.FileUtils;

public class FTPUtil extends BaseBean {
	private static String FTP_UTF8 = "UTF-8";
	private String server;
	private int port;
	private String userName;
	private String userPassword;
	private String charset;
	private FTPClient ftpClient = null;
	private boolean is_connected;
	public String message;

	public FTPUtil(String paramString1, int paramInt, String paramString2, String paramString3) {
		this.server = paramString1;
		this.port = paramInt;
		this.userName = paramString2;
		this.userPassword = paramString3;
		this.charset = FTP_UTF8;
		this.is_connected = false;
	}

	public FTPUtil(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4) {
		this.server = paramString1;
		this.port = paramInt;
		this.userName = paramString2;
		this.userPassword = paramString3;
		this.charset = paramString4;
		this.is_connected = false;
	}

	public boolean login() {
		try {
			this.ftpClient = new FTPClient();
			this.ftpClient.connect(this.server, this.port);
			this.ftpClient.login(this.userName, this.userPassword);
			this.ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			this.ftpClient.setControlEncoding(this.charset);
			this.ftpClient.setConnectTimeout(5000);
			this.ftpClient.setDataTimeout(120000);
			int j = this.ftpClient.getReplyCode();
			if (!(FTPReply.isPositiveCompletion(j))) {
				this.ftpClient.disconnect();
				this.is_connected = false;
				return is_connected;
			}
			this.is_connected = true;
		} catch (SocketException localSocketException) {
			this.is_connected = false;
			this.message = "FTP服务器连接超时，请检查FTP服务器地址及端口配置是否正确：FTPServer=" + this.server + "，Port=" + this.port + localSocketException.getMessage();
		} catch (IOException localIOException) {
			this.is_connected = false;
			this.message = "FTP服务器连接超时，请检查FTP服务器地址及端口配置是否正确：FTPServer=" + this.server + "，Port=" + this.port + localIOException.getMessage();
		}

		return is_connected;
	}

	public boolean logout() {
		try {
			if (this.ftpClient != null) {
				this.ftpClient.logout();
				this.ftpClient.disconnect();
				this.is_connected = false;
			}
		} catch (IOException localIOException) {
			this.message = "关闭FTP服务失败！" + localIOException.getMessage();
			return false;
		}
		return true;
	}

	public boolean isConnected() {
		return this.is_connected;
	}

	public boolean isDirExist(String paramString) {
		if (!(this.is_connected))
			return false;
		try {
			return this.ftpClient.changeWorkingDirectory(paramString);
		} catch (IOException localIOException) {
			this.message = "检查目录是否存在失败！" + localIOException.getMessage();
		}
		return false;
	}

	public boolean createDir(String paramString) {
		if (!(this.is_connected))
			return false;
		try {
			return this.ftpClient.makeDirectory(paramString);
		} catch (Exception localException) {
			this.message = "创建目录失败！" + localException.getMessage();
		}
		return false;
	}

	public String getWorkingDirectory() {
		if (!(this.is_connected))
			return "";
		try {
			return this.ftpClient.printWorkingDirectory();
		} catch (IOException localIOException) {
			this.message = "获取当前目录失败！" + localIOException.getMessage();
		}
		return "";
	}

	public boolean changeWorkingDirectory(String paramString) {
		if (!(this.is_connected))
			return false;
		try {
			return this.ftpClient.changeWorkingDirectory(paramString);
		} catch (IOException localIOException) {
			this.message = "进入 " + paramString + " 目录失败！" + localIOException.getMessage();
		}
		return false;
	}

	public boolean changeToParentDirectory() {
		if (!(this.is_connected))
			return false;
		try {
			return this.ftpClient.changeToParentDirectory();
		} catch (IOException localIOException) {
			this.message = "返回到上一层目录失败！" + localIOException.getMessage();
		}
		return false;
	}

	public List<FTPFile> getFileList(String paramString) {
		List localList = null;
		try {
			this.ftpClient.enterLocalPassiveMode();
			localList = Arrays.asList(this.ftpClient.listFiles(paramString));
		} catch (IOException localIOException) {
		}
		return localList;
	}

	public List<FTPFile> getFileList() {
		List localList = null;
		try {
			this.ftpClient.enterLocalPassiveMode();
			localList = Arrays.asList(this.ftpClient.listFiles());
		} catch (IOException localIOException) {
		}
		return localList;
	}

	public boolean removeDir(String paramString) {
		if (!(this.is_connected))
			return false;
		try {
			return this.ftpClient.removeDirectory(paramString);
		} catch (IOException localIOException) {
		}
		return false;
	}

	public boolean removeFile(String paramString) {
		if (!(this.is_connected))
			return false;
		try {
			return this.ftpClient.deleteFile(paramString);
		} catch (IOException localIOException) {
		}
		return false;
	}

	public boolean uploadFile(String paramString1, String paramString2) {
		return uploadFile(paramString1, null, paramString2);
	}

	public boolean uploadFile(String paramString1, String paramString2, String paramString3) {
		boolean bool = false;
		
		try {
			File localFile = new File(paramString1);
			if (!(localFile.exists())) {
				return false;
			}

			if (!(changeWorkingDirectory(paramString3))) {
				return false;
			}

			if ((paramString2 == null) || ("".equals(paramString2))) {
				paramString2 = localFile.getName();
			}
			paramString2 = new String(paramString2.getBytes("GBK"),"iso-8859-1");
			bool = this.ftpClient.storeFile(paramString2, FileUtils.openInputStream(localFile));
			if (!(bool)) {
				this.ftpClient.enterLocalPassiveMode();
				bool = this.ftpClient.storeFile(paramString2, FileUtils.openInputStream(localFile));
			}
		} catch (FileNotFoundException localFileNotFoundException) {
			bool = false;
			localFileNotFoundException.printStackTrace();
		} catch (IOException localIOException) {
			bool = false;
			localIOException.printStackTrace();
		}

		return bool;
	}

	public boolean uploadFile(File paramFile, String paramString) {
		return uploadFile(paramFile, null, paramString);
	}

	public boolean uploadFile(File paramFile, String paramString1, String paramString2) {
		boolean bool = false;
		BufferedInputStream localBufferedInputStream = null;
		try {
			if (!(changeWorkingDirectory(paramString2))) {
				return false;
			}

			if ((paramString1 == null) || ("".equals(paramString1))) {
				paramString1 = paramFile.getName();
			}
			localBufferedInputStream = new BufferedInputStream(new FileInputStream(paramFile));
			bool = this.ftpClient.storeFile(paramString1, localBufferedInputStream);
			if (!(bool)) {
				this.ftpClient.enterLocalPassiveMode();
				bool = this.ftpClient.storeFile(paramString1, localBufferedInputStream);
			}
			localBufferedInputStream.close();
		} catch (FileNotFoundException localFileNotFoundException) {
			bool = false;
		} catch (IOException localIOException) {
			bool = false;
		}

		return bool;
	}

	public boolean uploadFile(InputStream paramInputStream, String paramString1, String paramString2) {
		boolean bool = false;
		try {
			if (!(changeWorkingDirectory(paramString2))) {
				return false;
			}

			bool = this.ftpClient.storeFile(paramString1, paramInputStream);
			if (!(bool)) {
				this.ftpClient.enterLocalPassiveMode();
				bool = this.ftpClient.storeFile(paramString1, paramInputStream);
			}
			paramInputStream.close();
		} catch (FileNotFoundException localFileNotFoundException) {
			bool = false;
		} catch (IOException localIOException) {
			bool = false;
		}

		return bool;
	}

	public boolean downloadFile(String paramString1, String paramString2, String paramString3) {
		boolean bool = false;
		BufferedOutputStream localBufferedOutputStream = null;
		try {
			this.ftpClient.enterLocalPassiveMode();

//			if (!(changeWorkingDirectory(paramString1))) {
//				this.message = "FTP服务器 " + paramString1 + " 目录不存在，请检查！";
//				return false;
//			}

			List<FTPFile> localList = getFileList();
			if ((localList == null) || (localList.size() == 0)) {
				this.message = "FTP服务器当前路径下不存在文件！";
				return bool;
			}

			for (FTPFile localFTPFile : localList) {
				if (localFTPFile.getName().equals(paramString2)) {
					File localFile2 = new File(paramString3 + File.separator + localFTPFile.getName());
					localBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(localFile2));
					bool = this.ftpClient.retrieveFile(localFTPFile.getName(), localBufferedOutputStream);
				}
			}

			if(null != localBufferedOutputStream) {
				localBufferedOutputStream.close();
			}
		} catch (FileNotFoundException localFileNotFoundException) {
			bool = false;
			this.message = "文件下载失败！" + localFileNotFoundException.getMessage();
		} catch (IOException localIOException) {
			bool = false;
			this.message = "文件下载失败！" + localIOException.getMessage();
		}

		return bool;
	}

	public boolean uploadDir(String paramString1, String paramString2) {
		File localFile1 = new File(paramString1);
		if (!(localFile1.exists())) {
			return false;
		}
		try {
			this.ftpClient.changeWorkingDirectory(paramString2);
			this.ftpClient.makeDirectory(localFile1.getName());
			paramString2 = paramString2 + localFile1.getName() + "/";

			File[] arrayOfFile1 = localFile1.listFiles();
			for (File localFile2 : arrayOfFile1)
				if (localFile2.isDirectory()) {
					uploadDir(localFile2.getAbsolutePath().toString(), paramString2);
				} else
					uploadFile(localFile2.getAbsolutePath().toString(), paramString2);
		} catch (Exception localException) {
			return false;
		}

		return true;
	}

	public boolean downloadDir(String paramString1, String paramString2) {
		File localFile = new File(paramString2);
		if (!(localFile.exists())) {
			return false;
		}
		try {
			paramString1 = paramString1 + File.separator + localFile.getName();
			new File(paramString1).mkdirs();

			FTPFile[] arrayOfFTPFile1 = this.ftpClient.listFiles(paramString2);
			for (FTPFile localFTPFile : arrayOfFTPFile1) {
				String str = localFTPFile.getName();
				if (".".equals(str))
					continue;
				if ("..".equals(str)) {
					continue;
				}
				if (localFTPFile.isDirectory()) {
					downloadDir(paramString1, paramString2 + str + "/");
				} else
					downloadFile(paramString2, str, paramString1);
			}
		} catch (Exception localException) {
			return false;
		}

		return true;
	}
}

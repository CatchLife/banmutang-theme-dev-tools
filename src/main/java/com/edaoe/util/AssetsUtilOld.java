package com.edaoe.util;

import com.jfinal.log.Log;

import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Catch
 * @date 2018-09-13 上午10:39
 * @description
 */
public class AssetsUtilOld {
	private static final Log log = Log.getLog(AssetsUtilOld.class);
	private static final Charset UTF_8 = Charset.forName("UTF-8");
	private static final String JS_EXT = ".js", CSS_EXT = ".css";
	private static final String PROTOCOL = "^https?://.+$";
	
	// 考虑到线上环境基本不会频繁更改css,js文件为了性能故缓存
	private static ConcurrentMap<String, String> COMBO_MAP = new ConcurrentHashMap<>();
	
//	/**
//	 * 压缩工具
//	 * @param files 待压缩的文件列表 /assets/a.js,b.js
//	 * @return String 返回压缩完成之后的路径
//	 * @throws IOException 文件不存在时异常
//	 */
//	public static String getPath(Object[] files) throws IOException {
//		String rootPath = PathKit.getWebRootPath();
//		String path = COMBO_MAP.get(fileName);
//		if (StrKit.isBlank(path)) {
//			return combo(rootPath, fileName);
//		}
//		File assetsFile = new File(rootPath + path);
//		// 文件存在则直接返回路径
//		if (assetsFile.exists()) {
//			return path;
//		}
//		return combo(rootPath, fileName);
//	}
//
//	/**
//	 * 压缩css,js帮助
//	 * @param rootPath 项目路径
//	 * @param fileList 合并压缩的文件列表
//	 * @param isCss 是否是css
//	 * @param out 输出流
//	 * @throws IOException Io异常
//	 */
//	private static void compressorHelper(String rootPath, List<String> fileList, boolean isCss, Writer out) throws IOException {
//		try {
//			if (isCss) {
//				for (String path : fileList) {
//					boolean isRomte = isRomte(path);
//					// css 文件内容,并处理路径问题
//					String context = repairCss(ResourceUtils.getResource(path), path);
//					if(path.indexOf(".min.") > 0 || isRomte){// 对.min.css的css放弃压缩
//						out.append(context);
//					}else{
//						CssCompressor css = new CssCompressor(new StringReader(context));
//						css.compress(out, -1);
//					}
//				}
//			}else{
//				// nomunge: 混淆,verbose：显示信息消息和警告,preserveAllSemiColons：保留所有的分号 ,disableOptimizations 禁止优化
//				boolean munge = true, verbose = false, preserveAllSemiColons = false, disableOptimizations = false;
//				for (String path : fileList) {
//					boolean isRomte = isRomte(path);
//					String context = ResourceUtils.getResource(path);
//					if(path.indexOf(".min.") > 0 || isRomte){ // 对.min.js,和远程js放弃压缩
//						out.append(context);
//					}else{
//						JavaScriptCompressor compressor = new JavaScriptCompressor(new StringReader(context), new ErrorReporter() {
//							public void warning(String message, String sourceName,
//							                    int line, String lineSource, int lineOffset) {
//								if (line < 0) {
//									log.error("\n[WARNING] " + message);
//								} else {
//									log.error("\n[WARNING] " + line + ':' + lineOffset + ':' + message);
//								}
//							}
//							public void error(String message, String sourceName,
//							                  int line, String lineSource, int lineOffset) {
//								if (line < 0) {
//									log.error("\n[ERROR] " + message);
//								} else {
//									log.error("\n[ERROR] " + line + ':' + lineOffset + ':' + message);
//								}
//							}
//							public EvaluatorException runtimeError(String message, String sourceName,
//							                                       int line, String lineSource, int lineOffset) {
//								error(message, sourceName, line, lineSource, lineOffset);
//								return new EvaluatorException(message);
//							}
//						});
//						compressor.compress(out, -1, munge, verbose, preserveAllSemiColons, disableOptimizations);
//					}
//				}
//			}
//			out.flush();
//		} catch(IOException e){
//			throw e;
//		}
//	}
//
//	// css总得url图片正则——感谢Code Life(程式人生)的正则
//	private static final Pattern CSS_URL_PATTERN = Pattern.compile("url\\([\\s]*['\"]?((?!['\"]?https?://|['\"]?data:|['\"]?/).*?)['\"]?[\\s]*\\)");
//
//	/**
//	 * 将css文件里的图片相对路径修改为绝对路径
//	 * @param content 内容
//	 * @param path 路径
//	 * @return String css
//	 */
//	private static String repairCss(String content, String path){
//		// TODO：对于http、classpath、webjars的css合并压缩的css img路径能会合并出问题
//		if (!isRomte(path) && path.indexOf(':') != -1) {
//			path = path.substring(path.indexOf(':') + 1);
//		}
//		Matcher m = CSS_URL_PATTERN.matcher(content);
//		StringBuffer sb = new StringBuffer();
//		while (m.find()) {
//			String url = m.group(1).trim();
//			StringBuffer cssPath = new StringBuffer("url(").append(FilenameUtils.getFullPath(path)).append(url).append(")");
//			m.appendReplacement(sb, cssPath.toString());
//		}
//		m.appendTail(sb);
//		content = sb.toString();
//		return content;
//	}
//
//	/**
//	 * 压缩工具
//	 * @param fileName 待压缩的文件列表文件 /assets/assets.jjs
//	 * @param rootPath 项目路径
//	 * @return String 返回压缩完成之后的路径
//	 * @throws IOException 文件不存在时异常
//	 */
//	private static String combo(String rootPath, String fileName) throws IOException {
//		String assetsFile = ResourceUtils.getResource(fileName);
//		BufferedReader reader = new BufferedReader(new StringReader(assetsFile));
//
//		// 文件内容md5
//		StringBuilder fileMd5s = new StringBuilder();
//		List<String> fileList = new ArrayList<String>();
//
//		String lines = null;
//		while ((lines = reader.readLine()) != null) {
//			if (StrKit.isBlank(lines)) {
//				continue;
//			}
//			// 去除首尾空格
//			lines = lines.trim();
//			// #开头的行注释
//			if (lines.startsWith("#")) {
//				continue;
//			}
//			// 远程服务器上的资源文件,不参与MD5
//			if (isRomte(lines)) {
//				fileList.add(lines);
//				continue;
//			}
//			// MD5资源
//			String content = ResourceUtils.getResource(lines);
//			fileMd5s.append(HashKit.md5(content));
//			fileList.add(lines);
//		}
//		fileMd5s.append(HashKit.md5(assetsFile));
//
//		// 文件更改时间集合hex，MD5取中间8位
//		String hex = HashKit.md5(fileMd5s.toString()).substring(8, 16);
//		boolean isCss = true;
//		if (fileName.endsWith(".jjs")) {
//			isCss = false;
//		}
//		// /assets/assets.jjs
//		int fileNameIndex = fileName.lastIndexOf('/');
//		fileNameIndex = fileNameIndex == -1 ? 0 : fileNameIndex;
//		String comboName = fileName.substring(fileNameIndex, fileName.indexOf('.'));
//		String newFileName = comboName + '-'  + hex + (isCss ? CSS_EXT : JS_EXT);
//
//		String newPath = rootPath + "/assets" + newFileName;
//		System.out.println(newPath);
//		File file = new File(newPath);
//		// 判断文件是否已存在，已存在直接返回
//		if (file.exists()) {
//			return newFileName;
//		}
//		File dir = file.getParentFile();
//		if (!dir.exists()) {
//			dir.mkdirs();
//		}
//		// 将合并的结果写入文件，异常时将文件删除
//		OutputStream output = null;
//		Writer out = null;
//		try {
//			output = new FileOutputStream(newPath);
//			out = new OutputStreamWriter(output, UTF_8);
//			compressorHelper(rootPath, fileList, isCss, out);
//			// 装载文件路径
//			COMBO_MAP.put(fileName, newFileName);
//		} catch (Exception e) {
//			FileUtils.deleteQuietly(file);
//			log.error(e.getMessage(), e);
//			throw new RuntimeException(fileName + " 压缩异常，请检查是否有依赖问题！");
//		} finally {
//			IOUtils.closeQuietly(out);
//			IOUtils.closeQuietly(output);
//		}
//		return newFileName;
//	}
//
//	/**
//	 * 判断文件是否为远程资源文件,远程资源文件不进行压缩
//	 * @param path
//	 * @return
//	 */
//	private static boolean isRomte(String path){
//		if(StrKit.isBlank(path)){
//			return false;
//		}
//		return path.trim().matches(PROTOCOL);
//	}
}

/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2019 knokko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *  
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *******************************************************************************/
package nl.knokko.util.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.lwjgl.LWJGLUtil;

public class Natives {
	
	public static void prepare(File gameFolder){
		File destFolder = new File(gameFolder.getPath() + File.separator + "natives");
		if(!destFolder.exists()){
			System.out.println("natives folder does not exist; creating a new one");
			destFolder.mkdirs();
			try {
				URL url = Natives.class.getClassLoader().getResource("natives");
				URLConnection uc = url.openConnection();
				if(uc instanceof JarURLConnection){//not in development
					JarURLConnection jc = (JarURLConnection) uc;
					JarFile jar = jc.getJarFile();
					Enumeration<JarEntry> entries = jar.entries();
					while(entries.hasMoreElements()){
						JarEntry entry = entries.nextElement();
						String name = entry.getName();
						if(name.startsWith("natives")){
							File f = new File(gameFolder + File.separator + entry.getName());
							if(entry.isDirectory()){
								f.mkdirs();
							}
							else {
								copy(jar.getInputStream(entry), new FileOutputStream(f));
							}
						}
					}
				}
				else {//in development
					System.out.println("Not a jar connection (" + uc + ")");
					copyFile(new File("resources/natives"), gameFolder);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("natives folder already exists");
		}
		System.setProperty("org.lwjgl.librarypath", destFolder.getAbsolutePath() + "/" + LWJGLUtil.getPlatformName());
	}
	
	private static void copyFile(File source, File dest) throws IOException {
		File[] children = source.listFiles();
		if(children == null){//source is a file
			File newFile = new File(dest + File.separator + source.getName());
			copy(new FileInputStream(source), new FileOutputStream(newFile));
		}
		else {//source is a directory
			File newFolder = new File(dest + File.separator + source.getName());
			newFolder.mkdirs();
			for(File child : children){
				copyFile(child, newFolder);
			}
		}
	}
	
	private static void copy(InputStream input, OutputStream output) throws IOException{
		byte[] buffer = new byte[100000];
		int readBytes = input.read(buffer);
		while(readBytes != -1){
			output.write(buffer, 0, readBytes);
			readBytes = input.read(buffer);
		}
		input.close();
		output.close();
	}
}
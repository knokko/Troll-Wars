/* 
 * The MIT License
 *
 * Copyright 2018 knokko.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
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
 */
package nl.knokko.util.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import nl.knokko.gui.menu.main.GuiLoadGame;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitInputStream;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.bits.BitOutputStream;
import nl.knokko.util.bits.BooleanArrayBitInput;
import nl.knokko.util.bits.BooleanArrayBitOutput;
import static java.util.Calendar.*;

public final class Saver {
	
	//private static final File savesFolder = new File("trollwarssaves");
	
	//static {
		//savesFolder.mkdir();
	//}
	
	private static File savesFolder;
	
	private static String saveName;
	
	private static String loadName;
	
	//private static BitBuffer saveBuffer;
	
	private static Map<String,BooleanArrayBitOutput> saveBuffer;
	private static Map<String,BitInput> loadBuffer;
	
	public static void setSavesFolder(File folder){
		savesFolder = folder;
		savesFolder.mkdirs();
	}
	
	public static String validateSaveName(String name){
		try {
			String result = null;
			File testFile = new File(getSaveName(name));
			FileOutputStream output = new FileOutputStream(testFile);
			output.write(100);
			output.close();
			FileInputStream input = new FileInputStream(testFile);
			if(input.read() != 100) {
				result = "Input doesn't equal output";
			}
			input.close();
			testFile.delete();
			return result;
		} catch(Exception ex){
			ex.printStackTrace();
			if(ex.getLocalizedMessage().length() < 20)
				return ex.getClass().getSimpleName() + ": " + ex.getLocalizedMessage();
			return ex.getClass().getSimpleName() + ": " + ex.getLocalizedMessage().substring(0, 20) + "...";
		}
	}
	
	public static String getSaveName(String saveName){
		return getSaveName(saveName, System.currentTimeMillis());
	}
	
	private static String getSaveName(String saveName, long time){
		String absName = savesFolder.getAbsolutePath() + File.separator + "a";
		for(int i = 0; i < saveName.length(); i++){
			int c = (int) saveName.charAt(i);
			if(i != 0)
				absName += "k";
			absName += replace(Integer.toString(c));
		}
		String timeString = replace(time + "");
		new File(absName).mkdir();
		return absName + File.separatorChar + timeString + ".save";
	}
	
	private static String replace(String extra){
		extra = extra.replaceAll("0", "a");
		extra = extra.replaceAll("1", "b");
		extra = extra.replaceAll("2", "c");
		extra = extra.replaceAll("3", "d");
		extra = extra.replaceAll("4", "e");
		extra = extra.replaceAll("5", "f");
		extra = extra.replaceAll("6", "g");
		extra = extra.replaceAll("7", "h");
		extra = extra.replaceAll("8", "i");
		extra = extra.replaceAll("9", "j");
		return extra;
	}
	
	private static String getLoadName(String savedName, long savedTime){
		return getSaveName(savedName, savedTime);
	}
	
	public static void ensureFolder(String path){
		if(!isSaving())
			throw new IllegalStateException("The game is not saving!");
	}
	
	public static BitOutput save(String path, int startCapacity){
		if(!isSaving())
			throw new IllegalStateException("The game is not saving!");
		BooleanArrayBitOutput output = new BooleanArrayBitOutput(startCapacity);
		saveBuffer.put(path, output);
		return output;
	}
	
	/*
	public static void save(BooleanArrayBitOutput buffer, String path) {
		if(!isSaving())
			throw new IllegalStateException("The game is not saving!");
		saveBuffer.addJavaString(path);
		saveBuffer.addBooleanArray(buffer.getBooleans());
		count++;
	}
	
	public static void save(ByteArrayBitOutput buffer, String path) {
		if(!isSaving())
			throw new IllegalStateException("The game is not saving!");
		saveBuffer.addJavaString(path);
		saveBuffer.addByteArray(buffer.getBytes());
		count++;
	}
	*/
	
	public static BitInput load(String path) {
		if(!isLoading())
			throw new IllegalStateException("The game is not loading!");
		return loadBuffer.get(path);
	}
	
	public static void startLoading(String saveName, long savedTime) throws IOException {
		if(isLoading())
			throw new IllegalStateException("The game is already loading!");
		loadName = getLoadName(saveName, savedTime);
		BitInput input = new BitInputStream(new FileInputStream(loadName));
		//FileInputStream input = new FileInputStream(loadName);
		//byte[] countArray = new byte[4];
		//input.read(countArray);
		//count = ByteBuffer.wrap(countArray).getInt();
		loadBuffer = new TreeMap<String,BitInput>();
		int count = input.readInt();
		//BitInput inputBuffer = new BitInputStream(input);
		for(int i = 0; i < count; i++)
			loadBuffer.put(input.readJavaString(), new BooleanArrayBitInput(input.readBooleanArray()));
		input.terminate();
	}
	
	public static void stopLoading(){
		if(!isLoading())
			throw new IllegalStateException("The game is not loading!");
		loadBuffer = null;
		loadName = null;
	}
	
	public static void startSaving(String name){
		if(isSaving())
			throw new IllegalStateException("The game is already saving!");
		saveName = getSaveName(name);
		//saveBuffer = new BitBuffer(500);
		saveBuffer = new HashMap<String,BooleanArrayBitOutput>();
	}
	
	public static void stopSaving() throws IOException {
		if(!isSaving())
			throw new IllegalArgumentException("The game is not saving!");
		try {
			//FileOutputStream output = new FileOutputStream(saveName);
			BitOutputStream bos = new BitOutputStream(new FileOutputStream(saveName));
			//ByteBuffer countBuffer = ByteBuffer.allocate(4);
			//countBuffer.putInt(count);
			//output.write(countBuffer.array());
			bos.addInt(saveBuffer.size());
			Set<Entry<String,BooleanArrayBitOutput>> entrySet = saveBuffer.entrySet();
			for(Entry<String,BooleanArrayBitOutput> entry : entrySet){
				bos.addJavaString(entry.getKey());
				bos.addBooleanArray(entry.getValue().getBooleans());
			}
			//output.write(saveBuffer.toBytes());
			//output.close();
			bos.terminate();
			saveBuffer = null;
			saveName = null;
		} catch(IOException ex){
			saveBuffer = null;
			saveName = null;
			throw ex;
		}
	}
	
	public static boolean isSaving(){
		return saveName != null;
	}
	
	public static boolean isLoading(){
		return loadName != null;
	}
	
	public static String[] getSaveFiles(){
		File[] files = savesFolder.listFiles();
		ArrayList<String> saves = new ArrayList<String>(files.length);
		for(File file : files){
			if(file.isDirectory())
				saves.add(file.getName());
		}
		return saves.toArray(new String[saves.size()]);
	}
	
	public static SaveTime[] getSaveTimes(String save){
		File folder = new File(savesFolder + File.separator + save);
		File[] files = folder.listFiles();
		ArrayList<SaveTime> times = new ArrayList<SaveTime>(files.length);
		for(File file : files){
			if(!file.isDirectory()){
				try {
					long time = Long.parseLong(GuiLoadGame.replace(file.getName().substring(0, file.getName().length() - 5)));
					Calendar cal = new Calendar.Builder().setInstant(time).build();
					if(file.length() > 0)
						times.add(new SaveTime(time, cal.get(YEAR) + "/" + (cal.get(MONTH) + 1) + "/" + cal.get(DAY_OF_MONTH) + " " + cal.get(HOUR_OF_DAY) + ":" + cal.get(MINUTE) + ":" + cal.get(SECOND)));
				} catch(Exception ex){
					System.out.println("Can't load save " + file.getName());
					ex.printStackTrace();
				}
			}
		}
		return times.toArray(new SaveTime[times.size()]);
	}
	
	public static void deleteSaveFile(String save){
		deleteFile(new File(savesFolder + File.separator + save));
	}
	
	public static void deleteSaveTime(String save, long time){
		deleteFile(new File(savesFolder + File.separator + save + File.separator + replace(time + "") + ".save"));
	}
	
	private static void deleteFile(File file){
		File[] files = file.listFiles();
		if(files != null)
			for(File child : files)
				deleteFile(child);
		file.delete();
	}
	
	public static class SaveTime {
		
		private final long time;
		private final String text;
		
		private SaveTime(long milliTime, String text){
			time = milliTime;
			this.text = text;
		}
		
		public String getFolderName(){
			return time + "";
		}
		
		public String getText(){
			return text;
		}
		
		public long getMilliTime(){
			return time;
		}
	}
}

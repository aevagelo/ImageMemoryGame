package gr.stolis.games;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ImageMemoryUtils {
	public static void main(String[] args) throws IllegalArgumentException, InstantiationException, IllegalAccessException {
		/*
		String[] photos = {"apostolos", "odysseas", "ermis", "eirini", "andreas", "panagiota", "thanassis"};
		
		List<String> tmp = new ArrayList<String>();
		tmp.addAll(Arrays.asList(photos));
		System.out.println(tmp.getClass().getCanonicalName());
		List<String> ret = getRandomItems(tmp, 4, true);
		for (String string : ret) {
			System.out.println(string);
		}
		
		ret.addAll(ret);
		Collections.shuffle(ret);		
		
		System.out.println("uuuuuuuuuuuuuuuuuuuuu");

		for (String string : ret) {
			System.out.println(string);
		}
		System.out.println(ret.size());
		*/
		
		/*
		String[] selectedPhotos = getRandomItems(photos, 5, true);
		System.out.println(Arrays.toString(selectedPhotos));
		
		Integer[] sts = {1, 5, 8, 9, 34, 56, 34};
		Integer[] la = getRandomItems(sts, 5, false);
		System.out.println(Arrays.toString(la));
		*/
		
		//example with files
		File dir = new File("C:\\Users\\apostolos\\Desktop\\KARPENHSI STOMIO 2013");
		FileFilter filter = (file) -> { return file.getName().toLowerCase().endsWith("jpg"); };
		File[] rndSelectedFiles = getRandomListOfFiles(dir, 4, true, filter);
		for (int i = 0; i < rndSelectedFiles.length; i++) {
			System.out.println(rndSelectedFiles[i].getName());
		}
	}
	
	public static <T> List<T> getRandomItems(List<T> allItems, int numOfItemsToReturn, boolean unique) throws IllegalArgumentException, InstantiationException, IllegalAccessException{
		@SuppressWarnings("unchecked")
		T[] tmpArray = (T[])new Object[allItems.size()];
		allItems.toArray(tmpArray);
		T[] retArray = getRandomItems(tmpArray, numOfItemsToReturn, unique);
		@SuppressWarnings("unchecked")
		List<T> retList = allItems.getClass().newInstance();
		retList.addAll(Arrays.asList(retArray));
		return retList;
	}
	
	public static <E> E[] getRandomItems(E[] allItems, int numOfItemsToReturn, boolean unique) throws IllegalArgumentException{
		if (unique && numOfItemsToReturn>allItems.length) {
			throw new IllegalArgumentException("Number of items to Return are greater than the available options length and asked for unique returned results");
		}
		System.out.println(allItems.getClass().getCanonicalName());
		@SuppressWarnings("unchecked")
		E[] returnArray = (E[]) Array.newInstance(allItems[0].getClass(), numOfItemsToReturn); 
		Random rnd = new Random();
		if (!unique) {
			for (int i = 0; i < returnArray.length; i++) {
				returnArray[i] = (E) allItems[rnd.nextInt(allItems.length-1)];
			}
		} else {
			List<E> tmp = new ArrayList<E>();
			tmp.addAll(Arrays.asList(allItems));
			for (int i = 0; i < returnArray.length; i++) {
				int rndIndex = rnd.nextInt(tmp.size());
				returnArray[i] = (E) tmp.get(rndIndex);
				tmp.remove(rndIndex);
			}
		}
		return returnArray;
	}
	
	public static File[] getRandomListOfFiles(File dir, int numOfFilesToReturn, boolean unique, FileFilter filter) {
		if (!dir.isDirectory())
			throw new IllegalArgumentException("the path you specified must be a directory");
		if (unique && dir.listFiles(filter).length<numOfFilesToReturn)
			throw new IllegalArgumentException("Number of files to Return are greater than the available files and asked for unique returned results");
		
		File[] files = dir.listFiles(filter);
		return getRandomItems(files, numOfFilesToReturn, unique);
	}
	
}

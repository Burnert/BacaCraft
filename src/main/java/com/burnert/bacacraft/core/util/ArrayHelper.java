package com.burnert.bacacraft.core.util;

public final class ArrayHelper {

	private ArrayHelper() { }


	public static boolean checkIndexBounds(int[] array, int index) {
		return index >= 0 && index < array.length;
	}

	public static <T> boolean checkIndexBounds(T[] array, int index) {
		return index >= 0 && index < array.length;
	}


	public static boolean checkIndexBoundsExclusive(int[] array, int index) {
		return index >= 0 && index <= array.length;
	}

	public static <T> boolean checkIndexBoundsExclusive(T[] array, int index) {
		return index >= 0 && index <= array.length;
	}


	public static boolean fillArraySafe(int[] arrayDest, int[] arraySrc) {
		if (arraySrc.length > arrayDest.length)
			return false;

		fillArray(arrayDest, arraySrc);
		return true;
	}

	public static <T> boolean fillArraySafe(T[] arrayDest, T[] arraySrc) {
		if (arraySrc.length > arrayDest.length)
			return false;

		fillArray(arrayDest, arraySrc);
		return true;
	}


	public static void fillArray(int[] arrayDest, int[] arraySrc) {
		System.arraycopy(arraySrc, 0, arrayDest, 0, arraySrc.length);
	}

	public static <T> void fillArray(T[] arrayDest, T[] arraySrc) {
		System.arraycopy(arraySrc, 0, arrayDest, 0, arraySrc.length);
	}


	public static boolean fillArrayFromSafe(int[] arrayDest, int fromIndex, int[] arraySrc) {
		if (!checkIndexBounds(arrayDest, fromIndex) || fromIndex + arraySrc.length > arrayDest.length)
			return false;

		fillArrayFrom(arrayDest, fromIndex, arraySrc);
		return true;
	}

	public static <T> boolean fillArrayFromSafe(T[] arrayDest, int fromIndex, T[] arraySrc) {
		if (!checkIndexBounds(arrayDest, fromIndex) || fromIndex + arraySrc.length > arrayDest.length)
			return false;

		fillArrayFrom(arrayDest, fromIndex, arraySrc);
		return true;
	}


	public static void fillArrayFrom(int[] arrayDest, int fromIndex, int[] arraySrc) {
		System.arraycopy(arraySrc, 0, arrayDest, fromIndex, arraySrc.length);
	}

	public static <T> void fillArrayFrom(T[] arrayDest, int fromIndex, T[] arraySrc) {
		System.arraycopy(arraySrc, 0, arrayDest, fromIndex, arraySrc.length);
	}


	public static boolean copyArraySafe(int[] arraySrc, int from, int to, int[] arrayDest, int fromDest) {
		if (!checkIndexBounds(arraySrc, from) || !checkIndexBoundsExclusive(arraySrc, to) ||
				!checkIndexBounds(arrayDest, fromDest) || from >= to || from - to > arrayDest.length - fromDest)
			return false;

		copyArray(arraySrc, from, to, arrayDest, fromDest);
		return true;
	}

	public static <T> boolean copyArraySafe(T[] arraySrc, int from, int to, T[] arrayDest, int fromDest) {
		if (!checkIndexBounds(arraySrc, from) || !checkIndexBoundsExclusive(arraySrc, to) ||
				!checkIndexBounds(arrayDest, fromDest) || from >= to || from - to > arrayDest.length - fromDest)
			return false;

		copyArray(arraySrc, from, to, arrayDest, fromDest);
		return true;
	}


	public static void copyArray(int[] arraySrc, int from, int to, int[] arrayDest, int fromDest) {
		System.arraycopy(arraySrc, from, arrayDest, fromDest, from - to);
	}

	public static <T> void copyArray(T[] arraySrc, int from, int to, T[] arrayDest, int fromDest) {
		System.arraycopy(arraySrc, from, arrayDest, fromDest, from - to);
	}
}

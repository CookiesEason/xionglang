package com.sunxl.base.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DesUtil {
	private static String Algorithm = "DESUTIL";
	private static final char[][] sFunctionTable = { { '\14', '\4', '\r', '\1', '\2', '\15', '\11', '\b', '\3', '\n', '\6', '\f', '\5', '\t', '\7', '\15', '\7', '\4', '\14', '\2', '\r', '\1', '\n', '\6', '\f', '\11', '\t', '\5', '\3', '\b', '\4', '\1', '\14', '\b', '\r', '\6', '\2', '\11', '\15', '\f', '\t', '\7', '\3', '\n', '\5', '\15', '\f', '\b', '\2', '\4', '\t', '\1', '\7', '\5', '\11', '\3', '\14', '\n', '\6', '\r' },
			{ '\15', '\1', '\b', '\14', '\6', '\11', '\3', '\4', '\t', '\7', '\2', '\r', '\f', '\5', '\n', '\3', '\r', '\4', '\7', '\15', '\2', '\b', '\14', '\f', '\1', '\n', '\6', '\t', '\11', '\5', '\14', '\7', '\11', '\n', '\4', '\r', '\1', '\5', '\b', '\f', '\6', '\t', '\3', '\2', '\15', '\r', '\b', '\n', '\1', '\3', '\15', '\4', '\2', '\11', '\6', '\7', '\f', '\5', '\14', '\t' }, { '\n', '\t', '\14', '\6', '\3', '\15', '\5', '\1', '\r', '\f', '\7', '\11', '\4', '\2', '\b', '\r', '\7', '\t', '\3', '\4', '\6', '\n', '\2', '\b', '\5', '\14', '\f', '\11', '\15', '\1', '\r', '\6', '\4', '\t', '\b', '\15', '\3', '\11', '\1', '\2', '\f', '\5', '\n', '\14', '\7', '\1', '\n', '\r', '\6', '\t', '\b', '\7', '\4', '\15', '\14', '\3', '\11', '\5', '\2', '\f' },
			{ '\7', '\r', '\14', '\3', '\6', '\t', '\n', '\1', '\2', '\b', '\5', '\11', '\f', '\4', '\15', '\r', '\b', '\11', '\5', '\6', '\15', '\3', '\4', '\7', '\2', '\f', '\1', '\n', '\14', '\t', '\n', '\6', '\t', '\f', '\11', '\7', '\r', '\15', '\1', '\3', '\14', '\5', '\2', '\b', '\4', '\3', '\15', '\6', '\n', '\1', '\r', '\b', '\t', '\4', '\5', '\11', '\f', '\7', '\2', '\14' }, { '\2', '\f', '\4', '\1', '\7', '\n', '\11', '\6', '\b', '\5', '\3', '\15', '\r', '\14', '\t', '\14', '\11', '\2', '\f', '\4', '\7', '\r', '\1', '\5', '\15', '\n', '\3', '\t', '\b', '\6', '\4', '\2', '\1', '\11', '\n', '\r', '\7', '\b', '\15', '\t', '\f', '\5', '\6', '\3', '\14', '\11', '\b', '\f', '\7', '\1', '\14', '\2', '\r', '\6', '\15', '\t', '\n', '\4', '\5', '\3' },
			{ '\f', '\1', '\n', '\15', '\t', '\2', '\6', '\b', '\r', '\3', '\4', '\14', '\7', '\5', '\11', '\n', '\15', '\4', '\2', '\7', '\f', '\t', '\5', '\6', '\1', '\r', '\14', '\11', '\3', '\b', '\t', '\14', '\15', '\5', '\2', '\b', '\f', '\3', '\7', '\4', '\n', '\1', '\r', '\11', '\6', '\4', '\3', '\2', '\f', '\t', '\5', '\15', '\n', '\11', '\14', '\1', '\7', '\6', '\b', '\r' }, { '\4', '\11', '\2', '\14', '\15', '\b', '\r', '\3', '\f', '\t', '\7', '\5', '\n', '\6', '\1', '\r', '\11', '\7', '\4', '\t', '\1', '\n', '\14', '\3', '\5', '\f', '\2', '\15', '\b', '\6', '\1', '\4', '\11', '\r', '\f', '\3', '\7', '\14', '\n', '\15', '\6', '\b', '\5', '\t', '\2', '\6', '\11', '\r', '\b', '\1', '\4', '\n', '\7', '\t', '\5', '\15', '\14', '\2', '\3', '\f' },
			{ '\r', '\2', '\b', '\4', '\6', '\15', '\11', '\1', '\n', '\t', '\3', '\14', '\5', '\f', '\7', '\1', '\15', '\r', '\b', '\n', '\3', '\7', '\4', '\f', '\5', '\6', '\11', '\14', '\t', '\2', '\7', '\11', '\4', '\1', '\t', '\f', '\14', '\2', '\6', '\n', '\r', '\15', '\3', '\5', '\b', '\2', '\1', '\14', '\7', '\4', '\n', '\b', '\r', '\15', '\f', '\t', '\3', '\5', '\6', '\11' } };
	private char[] codedKey = new char[8];

	public DesUtil(String key) throws Exception {
		doKey(key.getBytes());
	}

	public DesUtil(byte[] key) throws Exception {
		doKey(key);
	}

	public DesUtil(char[] key) throws Exception {
		doKey(key);
	}

	public byte[] encrypt(byte[] source) throws Exception {
		return desProcess("ENCRYPT", source, source.length);
	}

	public byte[] encrypt(byte[] source, int len) throws Exception {
		return desProcess("ENCRYPT", source, len);
	}

	public byte[] decrypt(byte[] source) throws Exception {
		return desProcess("DECRYPT", source, source.length);
	}

	public byte[] decrypt(byte[] source, int len) throws Exception {
		return desProcess("DECRYPT", source, len);
	}

	public char[] encrypt(char[] source) throws Exception {
		return desProcess("ENCRYPT", source, source.length);
	}

	public char[] decrypt(char[] source) throws Exception {
		return desProcess("DECRYPT", source, source.length);
	}

	private byte[] desProcess(String dir, byte[] source, int l) throws Exception {
		char[] srcBuffer = StringUtil.convertByteToChar(source, l);

		char[] destBuffer = desProcess(dir, srcBuffer, l);

		byte[] dest = StringUtil.convertCharToByte(destBuffer, destBuffer.length);
		return dest;
	}

	private char[] desProcess(String dir, char[] s, int l) throws Exception {
		int rl = s.length / 8 * 8 + ((s.length % 8 == 0) ? 0 : 8);

		char[] dest = new char[rl];

		char[] buffer = new char[8];
		int offset = 0;
		while (l > 0) {
			System.arraycopy(s, offset, buffer, 0, (l > 8) ? 8 : l);
			for (int i = l; i < 8; ++i) {
				buffer[i] = ',';
			}

			desProcess8chars(dir, buffer);
			System.arraycopy(buffer, 0, dest, offset, 8);
			offset += 8;
			l -= 8;
		}
		return dest;
	}

	private void desProcess8chars(String dir, char[] s) throws Exception {
		int[] loop;
		char[] keyCopy = new char[8];
		int[] encryptLoop = { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };
		int[] decryptLoop = { 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1, 1 };

		char[] k = new char[6];

		if (dir.equals("ENCRYPT"))
			loop = encryptLoop;
		else
			loop = decryptLoop;

		doFirstChange(s);
		System.arraycopy(this.codedKey, 0, keyCopy, 0, 8);
		for (int i = 0; i < 16; ++i) {
			setKey(dir, keyCopy, loop[i], k);
			doMut(s, k);
		}
		for (int i = 0; i < 4; ++i) {
			char c = s[(i + 4)];
			s[(i + 4)] = s[i];
			s[i] = c;
		}
		doLastChange(s);
	}

	private void doKey(byte[] key) throws Exception {
		char[] charBuffer = StringUtil.convertByteToChar(key, key.length);

		doKey(charBuffer);
	}

	private void doKey(char[] key) throws Exception {
		if (key.length != 8) {
			throw new Exception("Invalid key length:" + key.length);
		}

		for (int i = 0; i < 8; ++i) {
			if (key[i] > 255)
				throw new Exception("Invalid character found in the des key");

		}

		char[] t = new char[8];
		for (int i = 0; i < 8; ++i) {
			t[i] = ',';
		}

		for (int i = 0; i < 8; ++i)
			for (int j = 0; j < 8; ++j) {
				int tmp115_114 = (7 - j);
				char[] tmp115_109 = t;
				tmp115_109[tmp115_114] = (char) (tmp115_109[tmp115_114] | (key[i] & 1 << j) >> j << i & 0xFF);
			}

		for (int i = 0; i < 4; ++i) {
			this.codedKey[i] = t[i];
			this.codedKey[(i + 4)] = t[(6 - i)];
		}
		int tmp190_189 = 3;
		char[] tmp190_186 = this.codedKey;
		tmp190_186[tmp190_189] = (char) (tmp190_186[tmp190_189] & 0xF0);
		this.codedKey[7] = (char) ((this.codedKey[7] & 0xF) << '\4' & 0xFF);
	}

	private void doFirstChange(char[] s) {
		char[] t = new char[8];

		for (int i = 0; i < 8; ++i) {
			t[i] = ',';
		}

		for (int i = 0; i < 8; ++i)
			for (int j = 0; j < 8; ++j) {
				int tmp47_46 = (7 - j);
				char[] tmp47_41 = t;
				tmp47_41[tmp47_46] = (char) (tmp47_41[tmp47_46] | (s[i] >> j & 0x1) << i & 0xFF);
			}

		for (int i = 0; i < 4; ++i) {
			s[i] = t[(2 * i + 1)];
			s[(i + 4)] = t[(2 * i)];
		}
	}

	private void doLastChange(char[] s) {
		char[] t = new char[8];

		for (int i = 0; i < 8; ++i) {
			t[i] = ',';
		}

		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 4; ++j) {
				int tmp42_41 = i;
				char[] tmp42_40 = t;
				tmp42_40[tmp42_41] = (char) (tmp42_40[tmp42_41] | (s[j] << 7 - i & 0x80) >> 2 * j + 1);
				int tmp69_68 = i;
				char[] tmp69_67 = t;
				tmp69_67[tmp69_68] = (char) (tmp69_67[tmp69_68] | (s[(j + 4)] << 7 - i & 0x80) >> 2 * j);
			}

		}

		System.arraycopy(t, 0, s, 0, 8);
	}

	private void doLeft(char[] s, int n) {
		doLeft(s, 0, n);
	}

	private void doLeft(char[] s, int offset, int n) {
		char l = (char) (255 << 8 - n & 0xFF);
		char t = (char) ((s[offset] & l) >> '\4');
		int tmp30_29 = (offset + 3);
		char[] tmp30_26 = s;
		tmp30_26[tmp30_29] = (char) (tmp30_26[tmp30_29] | t);
		for (int i = offset; i < offset + 3; ++i) {
			int tmp51_49 = i;
			char[] tmp51_48 = s;
			tmp51_48[tmp51_49] = (char) (tmp51_48[tmp51_49] << n);
			int tmp60_58 = i;
			char[] tmp60_57 = s;
			tmp60_57[tmp60_58] = (char) (tmp60_57[tmp60_58] & 0xFF);
			t = (char) ((s[(i + 1)] & l) >> 8 - n);
			int tmp88_86 = i;
			char[] tmp88_85 = s;
			tmp88_85[tmp88_86] = (char) (tmp88_85[tmp88_86] | t);
		}
		int tmp105_104 = (offset + 3);
		char[] tmp105_101 = s;
		tmp105_101[tmp105_104] = (char) (tmp105_101[tmp105_104] << n);
		int tmp115_114 = (offset + 3);
		char[] tmp115_111 = s;
		tmp115_111[tmp115_114] = (char) (tmp115_111[tmp115_114] & 0xFF);
	}

	private void doRight(char[] s, int n) {
		doRight(s, 0, n);
	}

	private void doRight(char[] s, int offset, int n) {
		for (int i = 0; i < n; ++i) {
			char l0 = (char) (s[offset] & 0x1);
			char l1 = (char) (s[(offset + 1)] & 0x1);
			int tmp29_28 = offset;
			char[] tmp29_27 = s;
			tmp29_27[tmp29_28] = (char) (tmp29_27[tmp29_28] >> '\1');
			int tmp39_38 = (offset + 1);
			char[] tmp39_35 = s;
			tmp39_35[tmp39_38] = (char) (tmp39_35[tmp39_38] >> '\1');
			int tmp49_48 = (offset + 1);
			char[] tmp49_45 = s;
			tmp49_45[tmp49_48] = (char) (tmp49_45[tmp49_48] | l0 << '\7' & 0xFF);
			l0 = (char) (s[(offset + 2)] & 0x1);
			int tmp77_76 = (offset + 2);
			char[] tmp77_73 = s;
			tmp77_73[tmp77_76] = (char) (tmp77_73[tmp77_76] >> '\1');
			int tmp87_86 = (offset + 2);
			char[] tmp87_83 = s;
			tmp87_83[tmp87_86] = (char) (tmp87_83[tmp87_86] | l1 << '\7' & 0xFF);
			int tmp105_104 = (offset + 3);
			char[] tmp105_101 = s;
			tmp105_101[tmp105_104] = (char) (tmp105_101[tmp105_104] >> '\1');
			int tmp115_114 = (offset + 3);
			char[] tmp115_111 = s;
			tmp115_111[tmp115_114] = (char) (tmp115_111[tmp115_114] | l0 << '\7' & 0xFF);
			if ((s[(offset + 3)] & 0xF) != 0) {
				int tmp142_141 = offset;
				char[] tmp142_140 = s;
				tmp142_140[tmp142_141] = (char) (tmp142_140[tmp142_141] | 0x80);
				int tmp154_153 = (offset + 3);
				char[] tmp154_150 = s;
				tmp154_150[tmp154_153] = (char) (tmp154_150[tmp154_153] & 0xF0);
			}
		}
	}

	private void setKey(String dir, char[] key, int n, char[] k) {
		for (int i = 0; i < 6; ++i) {
			k[i] = ',';
		}

		if (dir.equals("ENCRYPT")) {
			doLeft(key, n);
			doLeft(key, 4, n);
		}
		k[0] = (char) ((key[1] & 0x4) << '\5' | (key[2] & 0x80) >> '\1' | key[1] & 0x20 | (key[2] & 0x1) << '\4' | (key[0] & 0x80) >> '\4' | (key[0] & 0x8) >> '\1' | (key[0] & 0x20) >> '\4' | (key[3] & 0x10) >> '\4');

		k[1] = (char) ((key[1] & 0x2) << '\6' | (key[0] & 0x4) << '\4' | (key[2] & 0x8) << '\2' | (key[1] & 0x40) >> '\2' | (key[2] & 0x2) << '\2' | (key[2] & 0x20) >> '\3' | (key[1] & 0x10) >> '\3' | (key[0] & 0x10) >> '\4');

		k[2] = (char) ((key[3] & 0x40) << '\1' | (key[0] & 0x1) << '\6' | (key[1] & 0x1) << '\5' | (key[0] & 0x2) << '\3' | (key[3] & 0x20) >> '\2' | (key[2] & 0x10) >> '\2' | (key[1] & 0x8) >> '\2' | (key[0] & 0x40) >> '\6');

		k[3] = (char) ((key[5] & 0x8) << '\4' | (key[6] & 0x1) << '\6' | key[4] & 0x20 | (key[5] & 0x80) >> '\3' | (key[6] & 0x20) >> '\2' | (key[7] & 0x20) >> '\3' | (key[4] & 0x40) >> '\5' | (key[5] & 0x10) >> '\4');

		k[4] = (char) ((key[6] & 0x2) << '\6' | (key[6] & 0x80) >> '\1' | (key[4] & 0x8) << '\2' | key[6] & 0x10 | (key[5] & 0x1) << '\3' | (key[6] & 0x8) >> '\1' | (key[5] & 0x20) >> '\4' | (key[7] & 0x10) >> '\4');

		k[5] = (char) ((key[4] & 0x4) << '\5' | (key[7] & 0x80) >> '\1' | (key[6] & 0x40) >> '\1' | (key[5] & 0x4) << '\2' | (key[6] & 0x4) << '\1' | (key[4] & 0x1) << '\2' | (key[4] & 0x80) >> '\6' | (key[4] & 0x10) >> '\4');

		if (dir.equals("DECRYPT")) {
			doRight(key, n);
			doRight(key, 4, n);
		}
	}

	private void eExpand(char[] s, char[] r) {
		r[0] = (char) ((s[7] & 0x1) << '\7' | (s[4] & 0xF8) >> '\1' | (s[4] & 0x18) >> '\3');

		r[1] = (char) ((s[4] & 0x7) << '\5' | (s[4] & 0x1) << '\3' | (s[5] & 0x80) >> '\3' | (s[5] & 0xE0) >> '\5');

		r[2] = (char) ((s[5] & 0x18) << '\3' | (s[5] & 0x1F) << '\1' | (s[6] & 0x80) >> '\7');

		r[3] = (char) ((s[5] & 0x1) << '\7' | (s[6] & 0xF8) >> '\1' | (s[6] & 0x18) >> '\3');

		r[4] = (char) ((s[6] & 0x7) << '\5' | (s[6] & 0x1) << '\3' | (s[7] & 0x80) >> '\3' | (s[7] & 0xE0) >> '\5');

		r[5] = (char) ((s[7] & 0x18) << '\3' | (s[7] & 0x1F) << '\1' | (s[4] & 0x80) >> '\7');
	}

	private void pChange(char[] s) throws Exception {
		char[] t = null;

		if ((s == null) || (s.length < 4)) {
			throw new Exception("Invalid parameter");
		}

		t = new char[4];
		t[0] = (char) ((s[1] & 0x1) << '\7' | (s[0] & 0x2) << '\5' | (s[2] & 0x10) << '\1' | (s[2] & 0x8) << '\1' | s[3] & 0x8 | (s[1] & 0x10) >> '\2' | (s[3] & 0x10) >> '\3' | (s[2] & 0x80) >> '\7');

		t[1] = (char) (s[0] & 0x80 | (s[1] & 0x2) << '\5' | (s[2] & 0x2) << '\4' | (s[3] & 0x40) >> '\2' | s[0] & 0x8 | (s[2] & 0x40) >> '\4' | s[3] & 0x2 | (s[1] & 0x40) >> '\6');

		t[2] = (char) ((s[0] & 0x40) << '\1' | (s[0] & 0x1) << '\6' | (s[2] & 0x1) << '\5' | (s[1] & 0x4) << '\2' | (s[3] & 0x1) << '\3' | (s[3] & 0x20) >> '\3' | (s[0] & 0x20) >> '\4' | (s[1] & 0x80) >> '\7');

		t[3] = (char) ((s[2] & 0x20) << '\2' | (s[1] & 0x8) << '\3' | (s[3] & 0x4) << '\3' | (s[0] & 0x4) << '\2' | (s[2] & 0x4) << '\1' | (s[1] & 0x20) >> '\3' | (s[0] & 0x10) >> '\3' | (s[3] & 0x80) >> '\7');

		for (int i = 0; i < 4; ++i)
			s[i] = t[i];
	}

	private char findS(char[] s, int ns) {
		return findS(s, 0, ns);
	}

	private char findS(char[] s, int offset, int ns) {
		int col;
		int num;
		int index = 0;

		if ((ns == 1) || (ns == 5)) {
			col = (s[offset] & 0x80) >> '\6' | (s[offset] & 0x4) >> '\2';
			num = (s[offset] & 0x78) >> '\3';
			index = col * 16 + num;
		}
		if ((ns == 2) || (ns == 6)) {
			col = s[offset] & 0x2 | (s[(offset + 1)] & 0x10) >> '\4';
			num = (s[offset] & 0x1) << '\3' | (s[(offset + 1)] & 0xE0) >> '\5';
			index = col * 16 + num;
		}
		if ((ns == 3) || (ns == 7)) {
			col = (s[(offset + 1)] & 0x8) >> '\2' | (s[(offset + 2)] & 0x40) >> '\6';

			num = (s[(offset + 1)] & 0x7) << '\1' | (s[(offset + 2)] & 0x80) >> '\7';

			index = col * 16 + num;
		}
		if ((ns == 4) || (ns == 8)) {
			col = (s[(offset + 2)] & 0x20) >> '\4' | s[(offset + 2)] & 0x1;
			num = (s[(offset + 2)] & 0x1E) >> '\1';
			index = col * 16 + num;
		}
		return sFunctionTable[(ns - 1)][index];
	}

	private void doSFunction(char[] s, char[] r) {
		r[0] = (char) (findS(s, 1) << '\4' | findS(s, 2));
		r[1] = (char) (findS(s, 3) << '\4' | findS(s, 4));
		r[2] = (char) (findS(s, 3, 5) << '\4' | findS(s, 3, 6));
		r[3] = (char) (findS(s, 3, 7) << '\4' | findS(s, 3, 8));
	}

	private void fFunction(char[] s, char[] k, char[] m) throws Exception {
		char[] t = new char[6];

		eExpand(s, t);
		for (int i = 0; i < 6; ++i) {
			int tmp27_25 = i;
			char[] tmp27_23 = t;
			tmp27_23[tmp27_25] = (char) (tmp27_23[tmp27_25] ^ k[i]);
		}
		doSFunction(t, m);
		pChange(m);
	}

	private void doMut(char[] s, char[] k) throws Exception {
		char[] t = new char[4];

		fFunction(s, k, t);
		for (int i = 0; i < 4; ++i) {
			int tmp23_21 = i;
			char[] tmp23_20 = t;
			tmp23_20[tmp23_21] = (char) (tmp23_20[tmp23_21] ^ s[i]);
			s[i] = s[(i + 4)];
			s[(i + 4)] = t[i];
		}
	}

	public static byte[] decode(byte[] input, byte[] key) throws Exception {
		SecretKey deskey = new SecretKeySpec(key, Algorithm);

		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(2, deskey);

		byte[] clearByte = c1.doFinal(input);

		return clearByte;
	}
}
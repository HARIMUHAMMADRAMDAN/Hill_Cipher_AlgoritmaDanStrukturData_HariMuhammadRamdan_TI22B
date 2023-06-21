public class AffineCipher {
    private static final int[][] keyMatrix = {{4, 9}, {2, 5}};
    private static final int mod = 26;

    public static void main(String[] args) {
        String plaintext = "HARI MUHAMMAD RAMDAN";
        System.out.println("Plaintext: " + plaintext);

        String encryptedText = encrypt(plaintext);
        System.out.println("Encrypted Text: " + encryptedText);

        String decryptedText = decrypt(encryptedText);
        System.out.println("Decrypted Text: " + decryptedText);
    }

    public static String encrypt(String plaintext) {
        StringBuilder ciphertext = new StringBuilder();

        for (char ch : plaintext.toCharArray()) {
            if (Character.isLetter(ch)) {
                int value = ch - 'A';
                int encryptedValue = (keyMatrix[0][0] * value + keyMatrix[0][1]) % mod;
                ciphertext.append((char) (encryptedValue + 'A'));
            } else {
                ciphertext.append(ch);
            }
        }

        return ciphertext.toString();
    }

    public static String decrypt(String ciphertext) {
        StringBuilder plaintext = new StringBuilder();

        int determinantInverse = 0;
        int determinant = (keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0]) % mod;
        for (int i = 0; i < mod; i++) {
            if ((determinant * i) % mod == 1) {
                determinantInverse = i;
                break;
            }
        }

        int[][] inverseMatrix = {{keyMatrix[1][1], -keyMatrix[0][1]}, {-keyMatrix[1][0], keyMatrix[0][0]}};
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                inverseMatrix[i][j] = (inverseMatrix[i][j] * determinantInverse) % mod;
                if (inverseMatrix[i][j] < 0) {
                    inverseMatrix[i][j] += mod;
                }
            }
        }

        for (char ch : ciphertext.toCharArray()) {
            if (Character.isLetter(ch)) {
                int value = ch - 'A';
                int decryptedValue = (inverseMatrix[0][0] * (value - keyMatrix[0][1]) + inverseMatrix[0][1] * (value - keyMatrix[1][1])) % mod;
                if (decryptedValue < 0) {
                    decryptedValue += mod;
                }
                plaintext.append((char) (decryptedValue + 'A'));
            } else {
                plaintext.append(ch);
            }
        }

        return plaintext.toString();
    }
}

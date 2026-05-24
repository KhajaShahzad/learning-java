public class practice {
    public static void main(String[] args) {
        String input = "Hello World";
        StringBuilder result = new StringBuilder();

    for (int i = 0; i < input.length(); i++) {
    char ch = input.charAt(i);

    char xorChar = (char) (ch ^ 0); // XOR with 0 → no change

    result.append(xorChar);
    }
}
}

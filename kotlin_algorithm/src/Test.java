public class Test {
    static void func(String input) {
        int count = 0;
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int flag = i;
            if (chars[i] == '2') {
                int _count = 0;
                for (int j = i; j < chars.length; j++) {
                    if (chars[j] == '2') {
                        _count++;
                    }else{
                        flag = j;
                        break;
                    }
                }
                if (_count >= 2) {
                    count += _count -1;
                }
            }
            i = flag;

        }
        System.out.println(count);

    }

    public static void main(String[] args) {
        Test.func("12221");
        Test.func("122212223");
        Test.func("1222111232223");
        Test.func("22221112322123");
        Test.func("1222133222");
    }
}

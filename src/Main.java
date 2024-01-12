import de.glowman554.config.ConfigManager;
import de.glowman554.config.Savable;
import de.glowman554.config.auto.AutoSavable;
import de.glowman554.config.auto.Saved;
import de.glowman554.config.auto.processors.SavableArrayProcessor;
import de.glowman554.config.premade.IntegerSavable;

import java.io.File;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ConfigManager.BASE_FOLDER = new File("out/config");
        ConfigManager.BASE_FOLDER.mkdirs();

        ConfigManager manager = new ConfigManager("default", false);

        AutoSavable.debug = System.out::println;
        AutoSavable.register(TestData[].class, new SavableArrayProcessor(TestData::new, TestData[]::new));

        TestData s1 = new TestData();
        s1.loadDefaults();
        manager.setValue("test", s1);

        System.out.println(manager.loadValue("test", new TestData()));


        ConfigManager manager2 = new ConfigManager("default2", false);
        manager2.setValue("test", new Test1());

        Test2 result = (Test2) manager2.loadValue("test", new Test2());

        System.out.println(result.data.length);
        System.out.println(result.data[0].test_string);
    }

    public static class TestData extends AutoSavable {
        @Saved
        public String test_string;
        @Saved
        public String[] test_string_array;

        @Saved
        public int test_int;
        @Saved
        public int[] test_int_array;

        @Saved
        public long test_long;
        @Saved
        public long[] test_long_array;

        @Saved
        public double test_double;
        @Saved
        public double[] test_double_array;

        @Saved
        public boolean test_boolean;
        @Saved
        public boolean[] test_boolean_array;

        @Saved(remap = Savable.class)
        public IntegerSavable other_savable = new IntegerSavable(0);

        public void loadDefaults() {
            test_string = "hello";
            test_string_array = new String[]{"hello1", "hello2"};

            test_int = 123;
            test_int_array = new int[]{1, 2, 3};

            test_long = 123456;
            test_long_array = new long[]{12, 34, 56};

            test_double = 123.456;
            test_double_array = new double[]{1.2, 3.4, 5.6};

            test_boolean = true;
            test_boolean_array = new boolean[]{true, false, true};

            other_savable.setValue(123456789);
        }

        @Override
        public String toString() {
            return "RootSection{" + "test_string='" + test_string + '\'' + ", test_string_array=" + Arrays.toString(test_string_array) + ", test_int=" + test_int + ", test_int_array=" + Arrays.toString(test_int_array) + ", test_long=" + test_long + ", test_long_array=" + Arrays.toString(test_long_array) + ", test_double=" + test_double + ", test_double_array=" + Arrays.toString(test_double_array) + ", test_boolean=" + test_boolean + ", test_boolean_array=" + Arrays.toString(test_boolean_array) + ", other_savable=" + other_savable + '}';
        }
    }

    public static class Test1 extends AutoSavable {
        @Saved
        private TestData[] data = new TestData[]{new TestData(), new TestData(), new TestData()};

        public Test1() {
            data[0].loadDefaults();
            data[1].loadDefaults();
            data[2].loadDefaults();
        }
    }

    public static class Test2 extends AutoSavable {
        @Saved
        private TestData[] data = new TestData[]{};

    }
}
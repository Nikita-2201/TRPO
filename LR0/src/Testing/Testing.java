package Testing;

import CycleList.CycleList;
import Factory.UserFactory;
import Types.Users.UserType;

import java.io.IOException;

public class Testing {
    private UserFactory userFactory;
    private UserType userType;
    private CycleList cycleList;


    private static String INTEGER_FILE_SAVE = "saveInteger.dat";
    private static String FRACTION_FILE_SAVE = "saveFraction.dat";

    public void testIntegerType() throws IOException, InstantiationException {
        test("Integer", INTEGER_FILE_SAVE);
    }

    public void testFractionType() throws IOException, InstantiationException {
        test("Fraction", FRACTION_FILE_SAVE);
    }

    private void test(String typeUser, String saveFile) throws InstantiationException, IOException {
        userFactory = new UserFactory();
        System.out.printf("\n%s test:\n", typeUser);
        userType = userFactory.getBuilderByName(typeUser);
        cycleList = new CycleList();
        cycleList.add(userType.create());
        cycleList.add(userType.create());
        cycleList.add(userType.create());
        cycleList.add(userType.create());
        cycleList.add(userType.create());
        cycleList.add(userType.create());
        cycleList.add(userType.create());
        cycleList.add(userType.create());
        cycleList.add(userType.create());
        cycleList.add(userType.create());

        cycleList.printList();
        System.out.println("Saving file:");
        try {
            cycleList.save(userType, saveFile);
            System.out.println("Saved");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("\nGet node by index 6, 2, 4, 3:");
        System.out.println("6) = " + cycleList.getByIndex(6).toString());
        System.out.println("2) = " + cycleList.getByIndex(2).toString());
        System.out.println("4) = " + cycleList.getByIndex(4).toString());
        System.out.println("3) = " + cycleList.getByIndex(3).toString());

        System.out.println("\nDelete node by index 6, 2, 4, 3:");
        cycleList.remove(6);
        cycleList.remove(2);
        cycleList.remove(4);
        cycleList.remove(3);
        cycleList.printList();

        System.out.println("Sorting:");
        cycleList.sort(userType.getTypeComparator());
        cycleList.printList();

        System.out.println("Load from file:");
        cycleList.load(userType, saveFile);
        cycleList.printList();

        System.out.println("Iterator:");
        cycleList.forEach(System.out::println);

        System.out.println("Iterator reverse:");
        cycleList.forEachReverse(System.out::println);
    }
}

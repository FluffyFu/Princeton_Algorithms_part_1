/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class MyClass {
    private int value;
    public int value2;

    public MyClass(int value) {
        this.value = value;
        value2 = value;
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        MyClass that = (MyClass) y;
        if (this.value != that.value) return false;
        return true;
    }

    public static void main(String[] args) {

    }
}

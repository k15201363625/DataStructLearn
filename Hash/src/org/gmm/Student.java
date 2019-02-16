package org.gmm;

/**
 * 默认的hash函数使用地址 相同对象(同一个真实位置)拥有相同的hashcode
 */
public class Student {
    int grade;
    int cls;
    String firstName;
    String lastName;

    Student(int grade, int cls, String firstName, String lastName){
        this.grade = grade;
        this.cls = cls;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    //默认有地址作为哈希函数 可以自己实现 但是需要equals配合
    @Override
    public int hashCode(){
        int B = 31;
        int hash = 0;
        //包装类才有hashcode方法 sting float  java已经实现好了
        hash = hash * B + ((Integer)grade).hashCode();
        hash = hash * B + ((Integer)cls).hashCode();
        hash = hash * B + firstName.toLowerCase().hashCode();
        hash = hash * B + lastName.toLowerCase().hashCode();
        return hash;
    }
    @Override
    public boolean equals(Object o){
        //套路性写法 必须记住
        if(this == o)
            return true;
        if(o==null)
            return false;
        if(o.getClass()!=this.getClass())
            return false;

        Student other = (Student)o;

        return this.grade == other.grade &&
                this.cls == other.cls &&
                this.firstName.toLowerCase().equals(other.firstName.toLowerCase())&&
                this.lastName.toLowerCase().equals(other.lastName.toLowerCase());

    }
}

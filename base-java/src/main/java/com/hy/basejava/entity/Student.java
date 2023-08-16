package com.hy.basejava.entity;

import com.hy.basejava.lombok.Data;

/**
* Description: 克隆测试，测试结果：
 * 如果不实现Cloneable接口就用Object的clone方法，则会抛出CloneNotSupportedException
 * 实现接口后，调用Object的克隆方法会克隆一个新的对象，将原对象的内存块复制到新对象里，但是原对象的引用属性将不会被复制，
 * 也就是说，新对象里的属性地址引用将依然引用原对象里的属性的地址引用，如果要深度克隆，则需要再特殊处理一下新对象里的属性引用，也就是要再克隆一遍新对象里的属性对象
* @createDate: 2023/8/11 9:19
* @author haoyong
* @lastModifyBy haoyong
*/
public class Student implements Cloneable{

    String name;
    Integer age;
    boolean gender;
    String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Student clone(){
        try {
            return (Student) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                '}';
    }
}

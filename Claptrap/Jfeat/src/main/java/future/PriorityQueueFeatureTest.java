package future;

import java.util.PriorityQueue;

public class PriorityQueueFeatureTest {
    static class Person implements Comparable<Person> {
        int age;
        String name;

        public Person(String name, int age) {
            this.age = age;
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }

        @Override
        public int compareTo(Person o) {
            return this.age - o.getAge();
        }
    }

    public static void main(String[] args) {
        PriorityQueue priorityQueue = new PriorityQueue();

        priorityQueue.add(new Person("dd", 1));
        priorityQueue.add(new Person("bb", 2));
        priorityQueue.add(new Person("mm", 3));

        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue);
        Person p = new Person("bb", 2);
        priorityQueue.add(new Person("dd", 1));
        priorityQueue.add(p);
        priorityQueue.add(new Person("mm", 3));
        p.setAge(100);
        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.poll());
    }


}

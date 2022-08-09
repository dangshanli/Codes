package temptool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 8#901电费计算
public class Electric {
    List<Person> persons;
    double total;

    public Electric(double total, List<Person> persons) {
        this.total = total;
        this.persons = persons;
    }

    void outPrice() {
        double allFee = 0;
        for (Person person : persons) {
            allFee += person.getAirConditioningFee();
        }

        double avgPrice = (total - allFee) / persons.size();// 这里少一个人
        persons.stream().forEach(person -> {
            System.out.println(person.getName() + ":" + (person.getAirConditioningFee() + avgPrice) + "元");
        });
    }


    public static void main(String[] args) {
        Electric electric = new Electric(354.70, Arrays.asList(
                new Person("路章建", 508, 819),
                new Person("知秋", 37, 117),
                new Person("阴雨", 164, 251)));
        electric.outPrice();
    }

    static class Person {
        private double airConditioningPrice = 0.4;//空调电费共识单价0.4元
        private String name;
        private double start;
        private double end;

        double getUsedElectric() {
            return this.end - this.start;
        }

        double getAirConditioningFee() {
            return getUsedElectric() * airConditioningPrice;
        }


        public String getName() {
            return name;
        }

        public Person(String name, double start, double end) {
            this.name = name;
            this.start = start;
            this.end = end;
        }
    }
}

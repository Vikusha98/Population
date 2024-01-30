import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");

        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        // несовершеннолетние
        long underageCount = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();

        System.out.println("Количество несовершеннолетних: " + underageCount);

        // призывники м от 18 и до 27 лет
        List<String> conscripts = persons.stream()
                .filter(person -> person.getSex() == Sex.MAN)
                .filter(person -> person.getAge() >= 18 && person.getAge() <= 27)
                .map(Person::getFamily)
                .toList();

        System.out.println("Список фамилий призывников: " + conscripts);

        // работоспособные
        List<Person> potentialEmployees = persons.stream()
                .filter(person -> person.getEducation() == Education.HIGHER)
                .filter(person -> (person.getSex() == Sex.MAN && person.getAge() <= 65)
                        || (person.getSex() == Sex.WOMAN && person.getAge() <= 60))
                .sorted(Comparator.comparing(Person::getFamily))
                .toList();

        System.out.println("Отсортированный по фамилии список потенциально работоспособных людей с высшим образованием:");
        potentialEmployees.forEach(System.out::println);
    }
}
import java.io.File;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Main {

    public static void scriere(Set<InstrumentMuzical> lista) {
        try {
            ObjectMapper mapper=new ObjectMapper();
            mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator());
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            File file=new File("C:\\Users\\macov\\Desktop\\java_laboratoare\\lab7ex2\\src\\main\\resources\\Instrumente.json");
            mapper.writeValue(file,lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Set<InstrumentMuzical> citire() {
        try {
            File file=new File("C:\\Users\\macov\\Desktop\\java_laboratoare\\lab7ex2\\src\\main\\resources\\Instrumente.json");
            ObjectMapper mapper=new ObjectMapper();
            Set<InstrumentMuzical> persoane = mapper
                    .readValue(file, new TypeReference<Set<InstrumentMuzical>>(){});
            return persoane;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

        Set<InstrumentMuzical> p = new HashSet<>();
        p.add(new Chitara("Gibson", "1450", Tipchitara.ELECTRICA, 9));
        p.add(new Chitara("Yamaha", "3712", Tipchitara.ACUSTICA, 12));
        p.add(new Chitara("Fender", "3748", Tipchitara.CLASICA, 4));
        p.add(new SetTobe("Pearl", "3814", Tiptobe.ACUSTICE, 7, 7));
        p.add(new SetTobe("Yamaha", "2352", Tiptobe.ELECTRONICE, 4, 8));
        p.add(new SetTobe("Mapex", "3501", Tiptobe.ACUSTICE, 6, 4));

        scriere(p);
        Set<InstrumentMuzical> instrumente = citire();
        instrumente.forEach(System.out::println);

        Scanner sc = new Scanner(System.in);
        while(true)
        {
            System.out.println("0.Iesire");
            System.out.println("1.Dublicate");
            System.out.println("2.Sterge >3000ron");
            System.out.println("3.Afisare chitari");
            System.out.println("4.Afisare seturi de tobe");
            System.out.println("5.Chitara cu cele mai multe corzi");
            System.out.println("6.Afisare Tobe ordonat dupa nr de tobe");
            System.out.println("Optiunea dvs:");
            int opt = sc.nextInt();
            switch (opt) {
                case 0:
                    sc.close();
                    return;
                case 1:
                    InstrumentMuzical duplicate = new Chitara("Fender", "1000", Tipchitara.ELECTRICA, 6);
                    boolean added = instrumente.add(duplicate);

                    if (!added) {
                        System.out.println("Duplicate not allowed in the Set.");
                    } else {
                        System.out.println("Duplicate allowed in the Set.");
                    }
                    break;
                case 2:
                    instrumente.removeIf(c -> Integer.parseInt(c.getPret()) > 3000);
                    instrumente.forEach(System.out::println);
                    break;
                case 3:
                    instrumente.stream()
                            .filter(c->c instanceof Chitara)
                            .forEach(System.out::println);
                    break;
                case 4:  instrumente.stream()
                        .filter(c -> c.getClass().equals(SetTobe.class))
                        .forEach(System.out::println);
                    break;
                case 5:
                    instrumente.stream()
                            .filter(c -> c instanceof Chitara)
                            .max(Comparator.comparing(c -> ((Chitara) c).getNumarCorzi()))
                            .ifPresent(System.out::println);
                    break;
                case 6:
                    instrumente.stream()
                            .filter(c -> c instanceof SetTobe)
                            .sorted(Comparator.comparing(c -> ((SetTobe) c).getNrTobe()))
                            .forEach(System.out::println);
                    break;
                default:
                    System.out.println("Optiune invalida");
                    break;
            }
        }

    }
}

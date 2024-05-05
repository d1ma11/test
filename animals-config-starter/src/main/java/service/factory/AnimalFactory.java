package service.factory;

import dto.AbstractAnimal;
import dto.NamesProvider;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AnimalFactory {
    private static final Random random = new Random();
    private final NamesProvider namesProvider;

    public AnimalFactory(NamesProvider namesProvider) {
        this.namesProvider = namesProvider;
    }

    public enum AnimalType {
        PARROT, HAMSTER, TIGER, BEAR
    }

    /**
     * �������� �������� �� ��������� �����
     *
     * @return ��������
     */
    public AbstractAnimal getAnimal() {
        return getAnimal(AnimalType.values()[random.nextInt(AnimalType.values().length)]);
    }

    /**
     * �������� ��������� ������������� ����
     *
     * @param type ��� ���������
     * @return ��������
     */
    public AbstractAnimal getAnimal(AnimalType type) {

        return switch (type) {
            case PARROT -> new AbstractAnimal("PARROT", namesProvider.randomName(), "Lazy");
            case HAMSTER -> new AbstractAnimal("HAMSTER", namesProvider.randomName(), "Smart");
            case TIGER -> new AbstractAnimal("TIGER", namesProvider.randomName(), "Playful");
            case BEAR -> new AbstractAnimal("BEAR", namesProvider.randomName(), "Shy");
        };
    }
}

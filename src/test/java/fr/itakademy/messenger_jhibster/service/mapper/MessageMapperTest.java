package fr.itakademy.messenger_jhibster.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class MessageMapperTest {

    private MessageMapper messageMapper;

    @BeforeEach
    public void setUp() {
        messageMapper = new MessageMapperImpl();
    }
}

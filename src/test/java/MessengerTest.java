import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessengerTest {

    @Mock
    private MailServer mailServer;
    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private Messenger messenger;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInstanceIsNotNull() {
        assertNotNull(messenger);
    }

    @Test
    public void sendMailOnceTimeTest() {
        Client client = new Client("myCustomEmail");
        Template template = new Template("myCustomTemplate");

        mailServer.send(client.getEmail(), template.getTemplate());
        verify(mailServer, times(1)).send(client.getEmail(), template.getTemplate());

    }

    @Test
    public void sendMailWithWrongData() {
        mailServer.send(null, null);

        doThrow(new RuntimeException()).when(mailServer).send(null,null);


    }


    @Test
    public void preparedTemplateEngineOnceTimeTest() {
        Client client = new Client("myCustomEmail");
        Template template = new Template("myCustomTemplate");

        templateEngine.prepareMessage(template,client);

        verify(templateEngine, only()).prepareMessage(template,client);

    }

    @Test
    public void correctPreparedTemplateEngineTest() {
        Client client = new Client("myCustomEmail");
        Template template = new Template("myCustomTemplate");

        when(templateEngine.prepareMessage(template,client))
                .thenReturn(client.getEmail()+" : "+template.getTemplate());

    }

    @Test
    public void preparedTemplateEngineWithWrongDataTest() {
        Client client = null;
        Template template = null;

        when(templateEngine.prepareMessage(template,client)).thenReturn(null);

        doThrow(new RuntimeException()).when(templateEngine).prepareMessage(template,client);

    }

    @AfterEach
    public void tearDown() {
        messenger = null;
    }

}
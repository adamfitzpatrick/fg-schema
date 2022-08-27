package io.stepinto.fgSchema.IT;

import io.stepinto.FgSchema.exception.ConfigurationException;
import io.stepinto.FgSchema.exception.ProcessingException;
import io.stepinto.FgSchema.helpers.DynamicElementPostProcessor;
import io.stepinto.FgSchema.helpers.Marshaller;
import io.stepinto.FgSchema.schema.Category;
import io.stepinto.FgSchema.schema.ClientRoot;
import io.stepinto.FgSchema.schema.Dice;
import io.stepinto.FgSchema.schema.DynamicResultTableRow;
import io.stepinto.FgSchema.schema.DynamicTableRow;
import io.stepinto.FgSchema.schema.FgSchemaType;
import io.stepinto.FgSchema.schema.Table;
import io.stepinto.FgSchema.schema.TypedElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientRootFunctionalTestIT {
    private InputStream expectedTransitionalXmlStream;
    private String expectedTransitionalXml;
    private String expectedFinalXml;
    private ClientRoot clientRoot;
    @BeforeEach
    public void setup() throws IOException {
        expectedTransitionalXmlStream = this.getClass().getResourceAsStream("/functional-test-fixtures/transitional-client-expected.xml");
        assert expectedTransitionalXmlStream != null;
        expectedTransitionalXml = new String(expectedTransitionalXmlStream.readAllBytes());
        expectedTransitionalXmlStream = new ByteArrayInputStream(expectedTransitionalXml.getBytes());

        InputStream expectedFinalXmlStream = this.getClass().getResourceAsStream("/functional-test-fixtures/final-client-expected.xml");
        assert expectedFinalXmlStream != null;
        expectedFinalXml = new String(expectedFinalXmlStream.readAllBytes());

        DynamicResultTableRow result = new DynamicResultTableRow()
                .runtimeTagName("id-00001")
                .result(new TypedElement().type(FgSchemaType.string).value("result"));
        DynamicTableRow dynamicTableRow = new DynamicTableRow()
                .runtimeTagName("id-00001")
                .fromrange(new TypedElement().type(FgSchemaType.number).value(1))
                .torange(new TypedElement().type(FgSchemaType.number).value(1))
                .results(Collections.singletonList(result));
        Table table1 = new Table()
                .runtimeTagName("tab-one")
                .locked(new TypedElement().type(FgSchemaType.number).value(1))
                .name(new TypedElement().type(FgSchemaType.string).value("Name"))
                .description(new TypedElement().type(FgSchemaType.string).value("Description"))
                .output(new TypedElement().type(FgSchemaType.string).value("chat"))
                .notes(new TypedElement().type(FgSchemaType.formattedtext).value("notes"))
                .hiderollresults(new TypedElement().type(FgSchemaType.number).value(0))
                .mod(new TypedElement().type(FgSchemaType.number).value(0))
                .dice(new TypedElement().type(FgSchemaType.dice).value(Dice.d8.toString()))
                .labelcol1(new TypedElement().type(FgSchemaType.string).value("label"))
                .resultscols(new TypedElement().type(FgSchemaType.number).value(1))
                .tablerow(Collections.singletonList(dynamicTableRow));
        Category cat1 = new Category()
                .tables(Collections.singletonList(table1))
                .name("name")
                .baseicon("1")
                .decalicon("2");
        clientRoot = new ClientRoot()
                .tables(Collections.singletonList(cat1));
    }
    @Test
    public void marshallObjectToTransitionalXmlTest() throws ConfigurationException, ProcessingException {
        Marshaller<ClientRoot> marshaller = new Marshaller<>(ClientRoot.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        marshaller.marshal(clientRoot, outputStream);
        String actualTransitionalXml = outputStream.toString(StandardCharsets.UTF_8);

        assertEquals(expectedTransitionalXml, actualTransitionalXml);
    }

    @Test
    public void dynamicElementPostProcessingTest() throws ProcessingException, ConfigurationException {
        DynamicElementPostProcessor processor = new DynamicElementPostProcessor();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        processor.process(expectedTransitionalXmlStream, outputStream);
        assertEquals(expectedFinalXml, outputStream.toString(StandardCharsets.UTF_8));
    }
}

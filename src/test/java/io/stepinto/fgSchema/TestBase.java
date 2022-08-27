package io.stepinto.fgSchema;

import io.stepinto.FgSchema.schema.Category;
import io.stepinto.FgSchema.schema.ClientRoot;
import io.stepinto.FgSchema.schema.Dice;
import io.stepinto.FgSchema.schema.DynamicResultTableRow;
import io.stepinto.FgSchema.schema.DynamicTableRow;
import io.stepinto.FgSchema.schema.FgSchemaType;
import io.stepinto.FgSchema.schema.Table;
import io.stepinto.FgSchema.schema.TypedElement;

import java.util.Collections;

public class TestBase {
    protected ClientRoot clientRoot;

    protected void baseSetup() {
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
}

package spoon.utils;

import spoon.Launcher;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtLoop;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.regex.Pattern;

/**
 * Created by Florian on 13/12/2016.
 * Class used to capture all the loop operation in our code.
 */
public class CtLoopOperation implements CtOperationItf{

    private CtMethod method;
    private Launcher launcher;

    public CtLoopOperation (CtMethod method, Launcher launcher) {
        this.method = method;
        this.launcher = launcher;
        process();

    }

    public void process(){

        for (Object obj : this.method.getElements(new TypeFilter<>(CtLoop.class))) {
            //insert the method to capture the iteration and reset it at the en of the loop
            CtLoop loop = (CtLoop) obj;

            //insert our iteration method at beginning of the loop
            String addIteration = "utils.DebugManipulation.iterate(" + loop.getPosition().getLine() + ")";
            final CtCodeSnippetStatement addIterationSnippet = launcher.getFactory().Code().createCodeSnippetStatement(addIteration);
            loop.getBody().insertBefore(addIterationSnippet);

            //then insert our end-iteration method at the end of the loop
            String endIteration = "utils.DebugManipulation.resetIteration()";
            final CtCodeSnippetStatement endIterationSnippet = launcher.getFactory().Code().createCodeSnippetStatement(endIteration);
            loop.insertAfter(endIterationSnippet);

        }


    }
}

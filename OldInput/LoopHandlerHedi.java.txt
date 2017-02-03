package fil.iagl.opl.conan.handler;

import spoon.Launcher;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtLoop;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;

public class LoopHandler implements ElementHandler {

    @Override
    public void handle(CtMethod method, Launcher launcher) {
        for (Object obj : method.getElements(new TypeFilter<>(CtLoop.class))) {
            CtLoop loop = (CtLoop) obj;

            String addIteration = "fil.iagl.opl.conan.debug.DebugAssistant.iterate(" + loop.getPosition().getLine() + ")";
            final CtCodeSnippetStatement addIterationSnippet = launcher.getFactory().Code().createCodeSnippetStatement(addIteration);
            loop.getBody().insertBefore(addIterationSnippet);

            String endIteration = "fil.iagl.opl.conan.debug.DebugAssistant.resetIteration()";
            final CtCodeSnippetStatement endIterationSnippet = launcher.getFactory().Code().createCodeSnippetStatement(endIteration);
            loop.insertAfter(endIterationSnippet);

        }
    }

}

package jp.sourceforge.pdt_tools.reorderperspectives.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.WorkbenchWindow;

@SuppressWarnings("restriction")
public class ReorderHandler extends AbstractHandler {

	public ReorderHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		if (window instanceof WorkbenchWindow) {
			MWindow model = ((WorkbenchWindow) window).getModel();
			Object service = ((WorkbenchWindow) window)
					.getService(EModelService.class);
			if (service != null) {
				MPerspective perspective = ((EModelService) service)
						.getActivePerspective(model);
				if (perspective != null) {
					MElementContainer<MUIElement> container = perspective
							.getParent();
					List<MUIElement> list = container.getChildren();
					if (list.size() > 1) {
						ReorderDialog dialog = new ReorderDialog(
								window.getShell());
						dialog.setTitle("Reorder Perspectives in Perspective Switcher");
						dialog.setInput(list);
						if (dialog.open() == Window.OK) {
							MUIElement[] result = dialog.getElements();
							list.clear();
							for (MUIElement e : result) {
								list.add(e);
							}
							container.setToBeRendered(true);
							container.setSelectedElement(perspective);
						}
					}
				}
			}
		}
		return null;
	}
}

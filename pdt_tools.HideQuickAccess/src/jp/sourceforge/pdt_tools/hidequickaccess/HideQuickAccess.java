package jp.sourceforge.pdt_tools.hidequickaccess;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.State;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimElement;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.RegistryToggleState;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.internal.WorkbenchWindow;

@SuppressWarnings("restriction")
public class HideQuickAccess {

	public static final String HIDE_QUICK_ACCESS = "jp.sourceforge.pdt_tools.HideQuickAccess.command";
	public static final String QUICK_ACCESS = "org.eclipse.ui.window.quickAccess";

	public static void hideQuickAccess(IWorkbenchWindow window, boolean state) {
		if (window instanceof WorkbenchWindow) {
			MTrimBar topTrim = ((WorkbenchWindow) window).getTopTrim();
			for (MTrimElement element : topTrim.getChildren()) {
				if ("SearchField".equals(element.getElementId())) {
					element.setVisible(state);
					element.setToBeRendered(state);
					if (state) {
						IWorkbenchPage page = window.getActivePage();
						if (page instanceof WorkbenchPage) {
							((WorkbenchPage) page).resetToolBarLayout();
						}
					}
					break;
				}
			}
		}
	}

	public static boolean getCommandState(IWorkbenchWindow window) {
		boolean state = true;
		Object service = window.getService(ICommandService.class);
		if (service instanceof ICommandService) {
			Command command = ((ICommandService) service)
					.getCommand(HIDE_QUICK_ACCESS);
			if (command != null) {
				State toggleState = command
						.getState(RegistryToggleState.STATE_ID);
				state = (Boolean) toggleState.getValue();
			}
		}
		return state;
	}

}

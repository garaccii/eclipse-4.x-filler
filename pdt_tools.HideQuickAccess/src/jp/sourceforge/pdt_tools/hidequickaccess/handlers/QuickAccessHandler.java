package jp.sourceforge.pdt_tools.hidequickaccess.handlers;

import jp.sourceforge.pdt_tools.hidequickaccess.HideQuickAccess;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.IHandlerService;

public class QuickAccessHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		Object service = window.getService(IHandlerService.class);
		if (service instanceof IHandlerService) {
			try {
				if (HideQuickAccess.getCommandState(window)) {
					((IHandlerService) service).executeCommand(
							HideQuickAccess.HIDE_QUICK_ACCESS, null);
				}
				((IHandlerService) service).executeCommand(
						HideQuickAccess.QUICK_ACCESS, null);
			} catch (NotDefinedException e) {
			} catch (NotEnabledException e) {
			} catch (NotHandledException e) {
			}
		}
		return null;
	}

}

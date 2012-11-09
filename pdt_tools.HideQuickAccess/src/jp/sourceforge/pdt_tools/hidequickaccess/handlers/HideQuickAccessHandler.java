package jp.sourceforge.pdt_tools.hidequickaccess.handlers;

import jp.sourceforge.pdt_tools.hidequickaccess.HideQuickAccess;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class HideQuickAccessHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		boolean state = HandlerUtil.toggleCommandState(event.getCommand());
		HideQuickAccess.hideQuickAccess(window, state);
		return null;
	}

}

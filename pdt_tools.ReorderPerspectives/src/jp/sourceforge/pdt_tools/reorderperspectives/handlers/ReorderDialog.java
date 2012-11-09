package jp.sourceforge.pdt_tools.reorderperspectives.handlers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import jp.sourceforge.pdt_tools.reorderperspectives.Activator;

import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.impl.PerspectiveImpl;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListDialog;

@SuppressWarnings("restriction")
public class ReorderDialog extends ListDialog {

	private MUIElement[] elements;

	public ReorderDialog(Shell parent) {
		super(parent);
		setContentProvider(new ArrayContentProvider());
		setLabelProvider(new PerspectveLabelProvider());
	}

	@Override
	protected Control createDialogArea(Composite container) {
		Composite parent = (Composite) super.createDialogArea(container);
		Composite updown = new Composite(parent, SWT.NONE);
		updown.setLayoutData(new GridData(SWT.CENTER, SWT.BEGINNING, false,
				false));
		updown.setLayout(new GridLayout(2, true));
		Button up = new Button(updown, SWT.PUSH);
		up.setToolTipText("Up");
		up.setImage(Activator.getImageDescriptor(
				"icons/full/elcl16/search_prev.gif").createImage());
		up.setLayoutData(new GridData(SWT.RIGHT, SWT.BEGINNING, false, false));
		up.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = getTableViewer().getTable().getSelectionIndex();
				if (index > 0) {
					MUIElement tmp = elements[index];
					elements[index] = elements[index - 1];
					elements[index - 1] = tmp;
					getTableViewer().setInput(elements);
				}
			}
		});
		Button down = new Button(updown, SWT.PUSH);
		down.setToolTipText("Down");
		down.setImage(Activator.getImageDescriptor(
				"icons/full/elcl16/search_next.gif").createImage());
		down.setLayoutData(new GridData(SWT.LEFT, SWT.BEGINNING, false, false));
		down.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = getTableViewer().getTable().getSelectionIndex();
				if (index < getTableViewer().getTable().getItemCount() - 1) {
					MUIElement tmp = elements[index];
					elements[index] = elements[index + 1];
					elements[index + 1] = tmp;
					getTableViewer().setInput(elements);
				}
			}
		});
		return parent;
	}

	@Override
	protected Control createHelpControl(Composite parent) {
		return parent;
	}

	public void setInput(List<MUIElement> input) {
		elements = new MUIElement[input.size()];
		int i = 0;
		for (MUIElement element : input) {
			elements[i++] = element;
		}
		super.setInput(elements);
		setInitialSelections(new Object[] { elements[0] });
	}

	public MUIElement[] getElements() {
		return elements;
	}

	private class PerspectveLabelProvider extends LabelProvider {

		@Override
		public Image getImage(Object element) {
			if (element instanceof PerspectiveImpl) {
				String iconURI = ((PerspectiveImpl) element).getIconURI();
				try {
					ImageDescriptor descriptor = ImageDescriptor
							.createFromURL(new URL(iconURI));
					return descriptor.createImage();
				} catch (MalformedURLException e) {
				}
			}
			return super.getImage(element);
		}

		@Override
		public String getText(Object element) {
			if (element instanceof PerspectiveImpl) {
				return ((PerspectiveImpl) element).getLabel();
			}
			return super.getText(element);
		}
	}
}

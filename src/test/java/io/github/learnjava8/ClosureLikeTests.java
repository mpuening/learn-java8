package io.github.learnjava8;

import org.junit.Test;

public class ClosureLikeTests {

	public interface ButtonListener {
		void onClick(Button button);

		// Testing a default method
		default public int getId() {
			return hashCode();
		}
	}

	public static class Button {
		private final String name;
		private final ButtonListener listener;

		public Button(String name, ButtonListener listener) {
			this.name = name;
			this.listener = listener;
		}

		public String getName() {
			return name;
		}

		public void click() {
			listener.onClick(this);
		}
	}

	@Test
	public void testSomethingThatLooksLikeAClosure() {
		Button saveButton = new Button("Save", createButtonListener("Saving Data..."));
		Button cancelButton = new Button("Cancel", createButtonListener("Canceling..."));
		Button exitButton = new Button("Exit", createButtonListener("Exiting application..."));

		saveButton.click();
		cancelButton.click();
		exitButton.click();
	}

	protected ButtonListener createButtonListener(final String action) {
		return button -> System.out.println(button.getName() + " pressed. Action: " + action);
	}

	@Test
	public void testDefaultInterfaceMethod() {
		Button noopButton = new Button("Noop", new ButtonListener() {
			@Override
			public void onClick(Button button) {
				System.out.println("Button " + button.getName() + " pressed. Default Method: " + this.getId());
			}
		});

		noopButton.click();
	}
}

package org.powerbot.script.tools;

import java.util.ArrayList;
import java.util.List;

import org.powerbot.script.internal.methods.Items;
import org.powerbot.script.lang.ItemQuery;

public class Equipment extends ItemQuery<Item> {
	public static final int WIDGET = 1464;
	public static final int COMPONENT_CONTAINER = 14;
	public static final int WIDGET_GEAR = 1462;
	public static final int COMPONENT_GEAR_CONTAINER = 13;

	public Equipment(final MethodContext factory) {
		super(factory);
	}

	/**
	 * An enumeration of equipment slots.
	 *
	 * @author Timer
	 */
	public static enum Slot {
		HEAD(0, 0),
		CAPE(1, 1),
		NECK(2, 2),
		MAIN_HAND(3, 3),
		TORSO(4, 4),
		OFF_HAND(5, 5),
		LEGS(7, 7),
		HANDS(9, 9),
		FEET(10, 10),
		RING(12, 12),
		QUIVER(13, 13),
		AURA(14, 14),
		POCKET(14, 15);
		private final int storageIndex;
		private final int component;

		Slot(final int storageIndex, final int component) {
			this.storageIndex = storageIndex;
			this.component = component;
		}

		public int getStorageIndex() {
			return storageIndex;
		}

		public int getComponentIndex() {
			return component;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<Item> get() {
		final List<Item> items = new ArrayList<Item>(28);
		final int[][] data = ctx.items.getItems(Items.INDEX_EQUIPMENT);
		final Component component = getComponent();
		for (final Slot slot : Slot.values()) {
			final int index = slot.getStorageIndex();
			final Component c = component.getChild(slot.getComponentIndex());
			if (index < 0 || index >= data.length || data[index][0] == -1) {
				continue;
			}
			items.add(new Item(ctx, data[index][0], data[index][1], c));
		}
		return items;
	}

	/**
	 * Returns the {@link Item} at the spcified {@link Slot}.
	 *
	 * @param slot the {@link Slot} to get the {@link Item} at
	 * @return the {@link Item} in the provided slot
	 */
	public Item getItemAt(final Slot slot) {
		final int index = slot.getStorageIndex();
		final int[][] data = ctx.items.getItems(Items.INDEX_EQUIPMENT);
		final Component c = getComponent().getChild(slot.getComponentIndex());
		if (index >= data.length || data[index][0] == -1) {
			return new Item(ctx, -1, -1, c);
		}
		return new Item(ctx, data[index][0], data[index][1], c);
	}

	/**
	 * Returns the {@link Component} of the equipment display
	 *
	 * @return the {@link Component} of the equipment display
	 */
	public Component getComponent() {
		final Component gear = ctx.widgets.get(WIDGET_GEAR, COMPONENT_GEAR_CONTAINER);
		return gear.isVisible() ? gear : ctx.widgets.get(WIDGET, COMPONENT_CONTAINER);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Item getNil() {
		return new Item(ctx, -1, -1, null);
	}
}
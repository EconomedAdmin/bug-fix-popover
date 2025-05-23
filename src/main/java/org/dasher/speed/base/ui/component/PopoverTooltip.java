package org.dasher.speed.base.ui.component;


import java.io.Serial;

import com.vaadin.flow.component.ClickNotifier;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.popover.Popover;
import com.vaadin.flow.component.popover.PopoverPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The PopoverTooltip can be constructed by passing the content as a String and setting the .
 *
 * @author Andreas Buchmann *
 */
public class PopoverTooltip extends Popover {
	
	static final Logger LOG = LoggerFactory.getLogger( PopoverTooltip.class );
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	private static final int POPOVER_HOVER_DELAY = 0;
	private static final int POPOVER_HIDE_DELAY = 0;
	private static final String POPOVER_TOOLTIP_CLASS = "popover-tooltip";
	private static final String TOOLTIP_ATTRIBUTES_KEY = "custom-tooltip";
	
	
	/**
	 * Set defaults for Vaadin Popover to function as Tooltip
	 */
	private PopoverTooltip() {
		super();
		setOpenOnClick( false );
		setOpenOnHover( true );
		setModal( false );
		setHideDelay( POPOVER_HIDE_DELAY );
		setHoverDelay( POPOVER_HOVER_DELAY );
		addClassName( POPOVER_TOOLTIP_CLASS );
		setPosition( PopoverPosition.BOTTOM_END );
		
	}
	
	
	/**
	 * Constructor that initializes the PopoverTooltip with a <b>String</b> as content.
	 *
	 * @param htmlContent {@link String }
	 */
	private PopoverTooltip( String htmlContent ) {
		this();
		
		Html htmlContentSpan = new Html( htmlContent );
		add( htmlContentSpan );
	}
	
	
	
	public static void closeIfExists( Component component ) {
		if( component == null ) {
			return;
		}
		Object toolTipData = ComponentUtil.getData( component, TOOLTIP_ATTRIBUTES_KEY );
		
		if( toolTipData != null && toolTipData instanceof PopoverTooltip tooltip ) {
			tooltip.close();
		}
	}
	
	
	public static PopoverTooltip createOrRenew( Component target, String htmlString ) {
		return createOrRenew( target, htmlString, PopoverPosition.BOTTOM );
	}
	
	
	public static PopoverTooltip createOrRenew( Component target, String htmlString, PopoverPosition popoverPosition ) {
		if( target == null ) {
			return null;
		}
		Object toolTipData = ComponentUtil.getData( target, TOOLTIP_ATTRIBUTES_KEY );
		
		if( toolTipData != null ) {
			if( toolTipData instanceof PopoverTooltip tooltip ) {
				tooltip.removeFromParent();
				tooltip.setTarget( null );
			}
			else {
				LOG.warn( TOOLTIP_ATTRIBUTES_KEY + " is not a Popover Tooltip!" );
				return null;
			}
		}
		PopoverTooltip popoverTooltip = create( target, htmlString );
		if( popoverTooltip != null ) {
			popoverTooltip.setPosition( popoverPosition != null ? popoverPosition : PopoverPosition.BOTTOM );
			ComponentUtil.setData( target, TOOLTIP_ATTRIBUTES_KEY, popoverTooltip );
		}
		
		if( target instanceof ClickNotifier<?> clickNotifier ) {
			clickNotifier.addClickListener( event -> {
				PopoverTooltip.closeIfExists( target );
			} );
		}
		
		return popoverTooltip;
	}
	
	
	private static PopoverTooltip create( Component target, String htmlString ) {
		if( target == null || htmlString == null ) {
			throw new IllegalArgumentException( "Target and HTML string must not be null" );
		}
		var tt = new PopoverTooltip( htmlString );
		tt.setTarget( target );
		return tt;
	}
	
}

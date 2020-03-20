import React from 'react';

class ButtonMarquee extends React.Component {

	static defaultProps = {
		style: {
			element: 'marquee'
		},
	};

	static key = 'ButtonMarquee';

	handleClick(e) {
		//here we can handle the click event and also call applyStyle from our HOC
		this.applyStyle();
	}

	render() {
		const cssClass = `ae-button ${this.getStateClasses()}`;

		return (
			<button
				aria-pressed={cssClass.indexOf('pressed') !== -1}
				className={cssClass}
				style={{width: "3.5rem"}}
				data-type="button-marquee"
				onClick={(e) => this.handleClick(e)}
				tabIndex={this.props.tabIndex}>
				Custom Button
			</button>
		);
	}
}

export default AlloyEditor.Base.ButtonActionStyle(AlloyEditor.Base.ButtonStateClasses(AlloyEditor.Base.ButtonStyle(ButtonMarquee))
);
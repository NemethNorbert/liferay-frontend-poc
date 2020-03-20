import React from 'react';

import ClayForm, {ClayInput} from '@clayui/form';

export default () => {
	const spritemap = themeDisplay.getPathThemeImages() + '/lexicon/icons.svg';

	return (
		<ClayForm>
			<ClayForm.Group className="form-group-sm has-success">
				<label htmlFor="basicInput">Name</label>
				<ClayInput placeholder="Name" type="text" />
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						{"This is a form-feedback-item."}
					</ClayForm.FeedbackItem>
					<ClayForm.FeedbackItem>
						<ClayForm.FeedbackIndicator
						spritemap={spritemap}
						symbol="check-circle-full"
						/>
						{"This is a form-feedback-item with a check feedback indicator."}
					</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			</ClayForm.Group>
			<ClayForm.Group className="form-group-sm has-error">
				<label htmlFor="basicInput">Description</label>
				<textarea className="form-control" placeholder="Description" />
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						{"This is a form-feedback-item."}
					</ClayForm.FeedbackItem>
					<ClayForm.FeedbackItem>
						<ClayForm.FeedbackIndicator
						spritemap={spritemap}
						symbol="exclamation-full"
						/>
						{"This is a form-feedback-item with a error feedback indicator."}
					</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			</ClayForm.Group>
		</ClayForm>
	);
};
import ButtonMarquee from './buttons/ButtonMarquee';

export default function () {
	AlloyEditor.Buttons[
		ButtonMarquee.key
	] = AlloyEditor.ButtonMarquee = AlloyEditor.Base.ButtonActionStyle(
		AlloyEditor.Base.ButtonStateClasses(
			AlloyEditor.Base.ButtonStyle(ButtonMarquee)
		)
	);
}

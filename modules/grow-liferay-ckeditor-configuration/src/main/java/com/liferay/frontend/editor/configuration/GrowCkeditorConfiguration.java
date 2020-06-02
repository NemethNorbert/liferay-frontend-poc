/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.grow.liferay.ckeditor.configuration;

import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xuggler.XugglerUtil;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/* @formatter:off */
@Component(	immediate = true,
			property = {
					"editor.name=ckeditor",
					"service.ranking:Integer=100" },
			service = EditorConfigContributor.class)
/* @formatter:on */

public class GrowCkeditorConfiguration extends BaseEditorConfigContributor {
	private static final Log LOG = LogFactoryUtil.getLog(GrowCkeditorConfiguration.class);
	
	@Override
	public void populateConfigJSONObject(JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
			ThemeDisplay themeDisplay, RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		String extraPlugins = jsonObject.getString("extraPlugins");
		String extraYouLearnPlugins = "markdown";
		if (extraPlugins == null) {
			extraPlugins = extraPlugins + extraYouLearnPlugins;
		} else {
			extraPlugins = extraPlugins + "," + extraYouLearnPlugins;
		}
		jsonObject.put("extraPlugins", extraPlugins);
		jsonObject.put("autoParagraph", false);
		jsonObject.put("autoSaveTimeout", 3000);
		jsonObject.put("closeNoticeTimeout", 8000);
		jsonObject.put("height", 265);
		jsonObject.put("removePlugins", "scayt,wsc");
		jsonObject.put("youtube_responsive", true);
		jsonObject.put("pasteFromWordRemoveFontStyles", true);
	
		jsonObject.put("codeSnippet_theme", "googlecode");
		jsonObject.put("codeSnippet_languages", toJSONObject(
				"{bash: 'Bash', css: 'CSS', haskell: 'Haskell', html: 'HTML', java: 'Java', javascript: 'JavaScript', json: 'JSON', maxima: 'Maxima', php: 'PHP', python: 'Python', r: 'R', scala: 'Scala', tex: 'Tex'}"));
		jsonObject.put("font_names", "Open Sans, Helvetica,Arial,sans-serif");

		// see https://michaeljanea.com/ckeditor/mj-accordion
		jsonObject.put("allowedContent", true);
		jsonObject.put("mjAccordion_managePopupTitle", false);
		jsonObject.put("mjAccordion_managePopupContent", false);
		jsonObject.put("mj_variables_allow_html", true);

		// jsonObject.put("stylesSet", getStyleFormatsJSONArray(themeDisplay.getLocale()));

		jsonObject.put(
				"toolbar_empty",
				JSONFactoryUtil.createJSONArray());

		LOG.debug("Set custom toolbar_liferay");

		jsonObject.put("toolbar_liferay", getToolbarLiferayJSONArray(inputEditorTaglibAttributes));

		// LOG.debug("Set custom toolbar_simplemail");
		// jsonObject.put("toolbar_simplemail", getToolbarSimpleMailJSONArray(inputEditorTaglibAttributes));

	}

	protected boolean isShowSource(Map<String, Object> inputEditorTaglibAttributes) {
		return GetterUtil.getBoolean(inputEditorTaglibAttributes.get("liferay-ui:input-editor:showSource"));
	}

	protected JSONArray getToolbarLiferayJSONArray(Map<String, Object> inputEditorTaglibAttributes) {

		boolean inlineEdit = GetterUtil
				.getBoolean((String) inputEditorTaglibAttributes.get("liferay-ui:input-editor:inlineEdit"));

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(toJSONArray("['Bold', 'Italic', 'Underline', 'Strike']"));

		if (inlineEdit) {
			jsonArray.put(toJSONArray("['AjaxSave', '-', 'Restore']"));
		}

		jsonArray.put(toJSONArray("['Maximize', 'Styles']"));

		jsonArray.put(toJSONArray("['Undo', 'Redo', '-', 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', ]"));
		jsonArray.put(toJSONArray("['Image', 'Youtube', '-', 'Table', 'CodeSnippet', 'MJAccordion', ]"));
		jsonArray.put("/");
		jsonArray.put(toJSONArray("['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent']"));
		jsonArray.put(toJSONArray("['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock']"));
		jsonArray.put(toJSONArray("['Link', 'Unlink', 'Anchor']"));
		String buttons = "['Flash',";
		if (XugglerUtil.isEnabled()) {
			buttons = buttons.concat("'Audio','Video',");
		}
		buttons = buttons.concat("'-', 'SpecialChar']");
		jsonArray.put(toJSONArray(buttons));

		jsonArray.put(toJSONArray("['Find', 'Replace', 'SpellChecker', 'Scayt']"));
		jsonArray.put(toJSONArray("['SelectAll', 'RemoveFormat']"));
		jsonArray.put(toJSONArray("['Subscript', 'Superscript']"));
		if (!inlineEdit) {
			jsonArray.put(toJSONArray("['Source', 'Markdown']"));
		}

		return jsonArray;
	}

	// protected JSONArray getToolbarSimpleMailJSONArray(Map<String, Object> inputEditorTaglibAttributes) {
	// 	boolean inlineEdit = GetterUtil
	// 			.getBoolean((String) inputEditorTaglibAttributes.get("liferay-ui:input-editor:inlineEdit"));

	// 	JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

	// 	jsonArray.put(toJSONArray("['Bold', 'Italic', 'Underline', 'Strike']"));

	// 	if (inlineEdit) {
	// 		jsonArray.put(toJSONArray("['AjaxSave', '-', 'Restore']"));
	// 	}

	// 	jsonArray.put(toJSONArray("['Maximize', 'Styles']"));
	// 	jsonArray.put(toJSONArray("['Undo', 'Redo', '-', 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', ]"));
	// 	jsonArray.put(toJSONArray("['Table']"));
	// 	jsonArray.put("/");
	// 	jsonArray.put(toJSONArray("['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent']"));
	// 	jsonArray.put(toJSONArray("['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock']"));
	// 	jsonArray.put(toJSONArray("['Link', 'Unlink', 'Anchor']"));
	// 	jsonArray.put(toJSONArray("['SpecialChar']"));

	// 	jsonArray.put(toJSONArray("['Find', 'Replace']"));
	// 	jsonArray.put(toJSONArray("['SelectAll', 'RemoveFormat']"));
	// 	jsonArray.put(toJSONArray("['Subscript', 'Superscript']"));
	// 	if (!inlineEdit) {
	// 		jsonArray.put(toJSONArray("['Source']"));
	// 	}

	// 	return jsonArray;
	// }

	// protected JSONArray getStyleFormatsJSONArray(Locale locale) {
	// 	JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

	// 	jsonArray.put(getStyleFormatJSONObject("Introductie tekst", "p", "txt-introduction"));
	// 	jsonArray.put(getStyleFormatJSONObject("Standaard tekst", "p", "txt-regular"));
	// 	jsonArray.put(getStyleFormatJSONObject("Kop 1", "h3", "kop-1"));
	// 	jsonArray.put(getStyleFormatJSONObject("Kop 2", "h4", "kop-2"));
	// 	jsonArray.put(getStyleFormatJSONObject("Kop 3", "h4", "kop-3"));
	// 	jsonArray.put(getStyleFormatJSONObject("Kop 4", "h4", "kop-4"));
	// 	jsonArray.put(getStyleFormatJSONObject("Gearceerde tekst", "span", "txt-arcering"));
	// 	jsonArray.put(getStyleFormatJSONObject("Figuur/tabel bijschrift", "p", "txt-bijschrift"));
	// 	jsonArray.put(getStyleFormatJSONObject("Boxtekst", "p", "txt-block"));
	// 	jsonArray.put(getStyleFormatJSONObject("Button", "div", "ck-btn btn"));
	// 	jsonArray.put(getStyleFormatJSONObject("Accordeon kop", "div", "accordeon-header"));

	// 	return jsonArray;
	// }

	protected JSONObject getStyleFormatJSONObject(String styleFormatName, String element, String cssClass) {

		JSONObject styleJSONObject = JSONFactoryUtil.createJSONObject();

		if (Validator.isNotNull(cssClass)) {
			JSONObject attributesJSONObject = JSONFactoryUtil.createJSONObject();

			attributesJSONObject.put("class", cssClass);

			styleJSONObject.put("attributes", attributesJSONObject);
		}

		styleJSONObject.put("element", element);
		styleJSONObject.put("name", styleFormatName);

		return styleJSONObject;
	}
}
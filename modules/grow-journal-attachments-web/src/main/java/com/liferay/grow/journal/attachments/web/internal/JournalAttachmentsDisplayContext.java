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

package com.liferay.grow.journal.attachments.web.internal;

import com.liferay.asset.display.page.constants.AssetDisplayPageWebKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.util.JournalConverter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marcell Gyöpös
 */
public class JournalAttachmentsDisplayContext {

	public JournalAttachmentsDisplayContext(HttpServletRequest request) {
		_httpServletRequest = request;

		InfoDisplayObjectProvider infoDisplayObjectProvider =
			(InfoDisplayObjectProvider)request.getAttribute(
				AssetDisplayPageWebKeys.INFO_DISPLAY_OBJECT_PROVIDER);

		if (infoDisplayObjectProvider != null)
			_journalArticle =
				(JournalArticle)infoDisplayObjectProvider.getDisplayObject();

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public DDMFormValues getAttachments() throws PortalException {
		if (_ddmFormValues != null) {
			return _ddmFormValues;
		}

		if (_journalArticle == null) {
			return _ddmFormValues;
		}

		DDMStructure ddmStructure = _journalArticle.getDDMStructure();

		if (Validator.isNull(ddmStructure)) {
			return _ddmFormValues;
		}

		String content = _journalArticle.getContent();

		if (Validator.isNull(content)) {
			return _ddmFormValues;
		}

		Registry registry = RegistryUtil.getRegistry();

		JournalConverter journalConverter = registry.getService(
			registry.getServiceReference(JournalConverter.class));

		Fields fields = journalConverter.getDDMFields(ddmStructure, content);

		if (fields == null) {
			return _ddmFormValues;
		}

		_ddmFormValues = journalConverter.getDDMFormValues(
			ddmStructure, fields);

		return _ddmFormValues;
	}

	public List<String> getTitle() throws PortalException {
		if (Validator.isNull(_ddmFormValues)) return Collections.emptyList();

		List<String> titles = new ArrayList<>();

		for (DDMFormFieldValue value : _ddmFormValues.getDDMFormFieldValues()) {
			String valueType = value.getType();

			if (valueType.equals("ddm-documentlibrary")) {
				Value valueTemp = value.getValue();
				String unprocessedJSON = null;
				Map<Locale, String> valuesMap = valueTemp.getValues();

				if (valuesMap.get(_themeDisplay.getLocale()) != null) {
					unprocessedJSON = valuesMap.get(_themeDisplay.getLocale());
				}
				else {
					unprocessedJSON = valuesMap.get(
						valueTemp.getDefaultLocale());
				}

				JSONObject jsonObject = null;

				jsonObject = JSONFactoryUtil.createJSONObject(unprocessedJSON);

				long classPK = jsonObject.getLong("classPK");

				DLFileEntry dlFileEntry;
				dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(
					classPK);

				String title = dlFileEntry.getTitle();
				String size = _getSize(dlFileEntry.getSize());

				if (title.length() < 28) {
					titles.add(title + size);
				}
				else {
					StringBuilder sb = new StringBuilder();

					sb.append(title, 0, 23);
					sb.append("(...)");
					sb.append(".");
					sb.append(dlFileEntry.getExtension());
					sb.append(size);

					titles.add(sb.toString());
				}
			}
		}

		return titles;
	}

	private String _getSize(double size) {
		StringBuilder sb = new StringBuilder();

		sb.append(" ( ");

		String assignUnit = "B";

		if (size > 1000) {
			size = size / 1024;
			assignUnit = "Kb";
		}

		if (size > 1000) {
			size = size / 1024;
			assignUnit = "Mb";
		}

		sb.append(String.format("%1$,.2f", size));
		sb.append(" ");
		sb.append(assignUnit);
		sb.append(" )");

		return sb.toString();
	}

	private DDMFormValues _ddmFormValues = null;
	private HttpServletRequest _httpServletRequest = null;
	private JournalArticle _journalArticle = null;
	private ThemeDisplay _themeDisplay = null;

}
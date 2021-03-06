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

package com.grow.liferay.ckeditor.configuration.servlet;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.util.StreamUtil;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Chema Balsas
 */
@Component(service = DynamicInclude.class)
public class GrowCkeditorDynamicInclude implements DynamicInclude {

	@Override
	public void include(HttpServletRequest request, HttpServletResponse response, String key) throws IOException {
		Bundle bundle = _bundleContext.getBundle();

		URL entryURL = bundle.getEntry("/META-INF/resources/ckeditor/extraplugins/markdown/plugin.js");
		StreamUtil.transfer(entryURL.openStream(), response.getOutputStream(), false);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("com.liferay.frontend.editor.ckeditor.web#ckeditor#onEditorCreate");
	}

	@Activate
	protected void activate(BundleContext bundlecontext) {
		_bundleContext = bundlecontext;
	}

	private BundleContext _bundleContext;
}
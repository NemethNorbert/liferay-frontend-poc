package com.liferay.docs.portlet.portlet;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Map;
import java.util.Objects;
import com.liferay.portal.kernel.util.Validator;

@Component(
    immediate = true,
    property = {
      "editor.name=alloyeditor",
      "service.ranking:Integer=100"
    },
    service = EditorConfigContributor.class  
)
public class AlloyEditorLinkConfigContributor extends BaseEditorConfigContributor {

  @Override
  public void populateConfigJSONObject(
         JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
         ThemeDisplay themeDisplay,
         RequestBackedPortletURLFactory requestBackedPortletURLFactory) {
          
                JSONArray allowedTargetsJSONObject = JSONUtil.putAll(
                        JSONUtil.put("label","_self (same tab)").put("value","_self"),
                        JSONUtil.put("label","_blank (new tab)").put("value","_blank")
                );

                JSONObject linkEditJSONObject = JSONUtil.put(
                        "allowedTargets", allowedTargetsJSONObject
                );

                JSONObject buttonCfgJSONObject =
                        JSONUtil.put("link", linkEditJSONObject).put("linkEdit",
                                linkEditJSONObject).put("linkEditBrowse", linkEditJSONObject);

                jsonObject.put(
                        "buttonCfg", buttonCfgJSONObject
                );
  }
}
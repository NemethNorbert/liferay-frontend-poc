
import React, {useState} from 'react';

import ClayTabs from '@clayui/tabs';

import CardList from './CardList.es';
import ClayAutocompleteDropdown from './ClayAutocompleteDropdown.es';
import ClayForm from './ClayForm.es';
import ReducerCounter from './ReducerCounter.es';

export default (props) => {
	const [activeTabKey, setActiveTabKey] = useState(0);

	const tabs = [
		['Clay Autocomplete Dropdown', <ClayAutocompleteDropdown />],
		['Clay Form', <ClayForm />],
		['Reducer Counter', <ReducerCounter />],
		['Clay Card', <CardList />]
	];

	return (
		<>
			<h1>{'PoC MVC React Portlet'}</h1>

			<div>Data passed with renderRequest: {props.data.message}</div>

			<div className="container-fluid my-3">
				<div className="row">
					<div className="col-md-4">
						<ClayTabs className="d-flex flex-column">
							{tabs.map((tab, i) => (
								<ClayTabs.Item
									active={activeTabKey == i}
									innerProps={{
										"aria-controls": `tabpanel-${i + 1}`
									}}
									onClick={() => setActiveTabKey(i)}
									key={i}
								>
									{tab[0]}
								</ClayTabs.Item>
							))}
						</ClayTabs>
					</div>
					<div className="col-md-8">
						<ClayTabs.Content activeIndex={activeTabKey}>
							{tabs.map((tab, i) => (
								<ClayTabs.TabPane
									aria-labelledby={`tab-${i + 1}`}
									key={i}>
									{tab[1]}
								</ClayTabs.TabPane>
							))}
						</ClayTabs.Content>
					</div>
				</div>
			</div>
		</>
	);
}
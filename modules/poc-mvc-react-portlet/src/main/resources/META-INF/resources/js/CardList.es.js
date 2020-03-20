import React, {useState} from 'react';
import ClayCard from '@clayui/card';
import {useResource} from '@clayui/data-provider';
import ClayIcon from '@clayui/icon';


export default () => {
    const spritemap = Liferay.ThemeDisplay.getPathThemeImages() + '/lexicon/icons.svg';
    const url = Liferay.ThemeDisplay.getPortalURL() + "/api/jsonws/user/get-company-users?companyId=" + Liferay.ThemeDisplay.getCompanyId() + "&start=0&end=5&p_auth=" + Liferay.authToken

    const [networkStatus, setNetworkStatus] = useState(4);

    const { resource } = useResource({
		fetchPolicy: 'cache-first',
		link: url,
		onNetworkStatusChange: setNetworkStatus
	});

    const error = networkStatus === 5;
    return (
        <div className="row">
            {(error || !resource) && (
                <div className="col-md-6">
                    <ClayCard horizontal>
                        <ClayCard.Row style={{"padding":"1rem"}}>
                            <div className="autofit-col">
                                    <ClayIcon 
                                        symbol="exclamation-full"
                                        spritemap={spritemap}
                                        style={{"margin": "1rem"}}
                                    />
                            </div>
                            <div className="autofit-col autofit-col-expand autofit-col-gutters">
                                <section className="autofit-section">
                                    <ClayCard.Description displayType="title">
                                        No resource were found :(
                                    </ClayCard.Description>
                                    <ClayCard.Description truncate={false} displayType="text">
                                        Are you a guest? -> Login!
                                    </ClayCard.Description>
                                </section>
                            </div>
                        </ClayCard.Row>
                    </ClayCard>
                </div>
            )}
            {!error &&
                resource &&
                resource.map(item => 
                    <div className="col-md-6">
                        <ClayCard horizontal key={item.uuid}>
                            <ClayCard.Row style={{"padding":"1rem"}}>
                                <div className="autofit-col">
                                    <ClayIcon 
                                        symbol="user"
                                        spritemap={spritemap}
                                        style={{"margin": "1rem"}}
                                    />
                                </div>
                                <div className="autofit-col autofit-col-expand autofit-col-gutters">
                                    <section className="autofit-section">
                                        <ClayCard.Description displayType="title">
                                            {item.firstName + " " + item.lastName}
                                        </ClayCard.Description>
                                        <ClayCard.Description truncate={false} displayType="text">
                                            {item.greeting}
                                        </ClayCard.Description>
                                    </section>
                                </div>
                            </ClayCard.Row>
                        </ClayCard>
                    </div>
            )}   
        </div>
    );
};
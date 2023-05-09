import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/calendar-day">
        Calendar Day
      </MenuItem>
      <MenuItem icon="asterisk" to="/session">
        Session
      </MenuItem>
      <MenuItem icon="asterisk" to="/workout">
        Workout
      </MenuItem>
      <MenuItem icon="asterisk" to="/activity">
        Activity
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;

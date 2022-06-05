# Deploy Scripts

Once the application is deploy script are used for starting, monitoring and stopping the application.

## Variables

Common variables used by the scripts are set in:

* `setenviroment_app.sh`

## Start Script

`start.sh`

### Requirements

* Has to be run from the deploy folder on the application node

## Description

Takes a user input to start a version of the application, if there is no user input the application uses the current
version. Creates a file with the current version running, with the name being set by
commonVersionFile `setenviroment_app.sh`, which can be used by other scripts.

## Shutdown Script

`shutdown.sh`

### Requirements

* Has to be run from the deploy folder on the application node

### Description

The script shuts down the application based on the version specified in the commonVersionFile named
in `setenviroment_app.sh`.
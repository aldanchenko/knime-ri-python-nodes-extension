Skeleton for Python based KNIME nodes with sample code as described [here](https://tech.knime.org/developer-guide).

[![Build Status](https://travis-ci.org/rational-insights/https://github.com/aldanchenko/knime-rational-insights-plugin.git.svg?branch=master)](https://travis-ci.org/rational-insights/https://github.com/aldanchenko/knime-rational-insights-plugin.git)
[![SonarCloud Gate](https://sonarcloud.io/api/badges/gate?key=com.rationalinsights:rationalinsights-knime-plugin)](https://sonarcloud.io/dashboard?id=us.rationalinsights:rationalinsights-knime-plugin)
[![SonarCloud Coverage](https://sonarcloud.io/api/badges/measure?key=com.rationalinsights:rationalinsights-knime-plugin&metric=coverage)](https://sonarcloud.io/component_measures/domain/Coverage?id=us.rationalinsights:rationalinsights-knime-plugin)

This project uses [Eclipse Tycho](https://www.eclipse.org/tycho/) to perform build steps.

# Installation

Requirements:

* KNIME, https://www.knime.org, version 4.0 or higher

Steps to get the HelloWorldRationalInsights KNIME node inside KNIME:

1. Goto Help > Install new software ... menu
2. Press add button
3. Fill text fields with url of update site which contains this node.
4. Select --all sites-- in `work with` pulldown
5. Select the node
6. Install software
7. Restart KNIME

# Usage

1. Create a new KNIME workflow.
2. Find node in Node navigator panel.
3. Drag node to workflow canvas.

# Build

To build the node extension and verify the tests run with the following command:
```
mvn verify
```

Make sure all code is commited as the snapshot version is determined by git commit timestamp.

An Eclipse update site will be made in `p2/target/repository` directory.
The update site can be used to perform a local installation.

## Continuous Integration

Configuration files to run Continuous Integration builds on Linux (Travis-CI), OS X (Travis-CI) and Windows (AppVeyor) are present.

See `./.travis.yml` file how to trigger a Travis-CI build for every push or pull request.
Also see `./.travis.yml` file how to perform a [SonarCloud](https://sonarcloud.io/) analysis and code coverage.

See `./appveyor.yml` file how to run on https://www.appveyor.com .

To cite the KNIME node, a [DOI](https://en.wikipedia.org/wiki/Digital_object_identifier) can be generated when a GitHub release is made. To enable, the GitHub repository must be connected on https://zenodo.org/account/settings/github/ . The connection must be made before creating a GitHub release.
To [cite the software](https://research-software.org/citation/developers/) a human and computer readable file called `CITATION.cff` is included.

# Development

Steps to get development environment setup based on https://github.com/knime/knime-sdk-setup#sdk-setup:

1. Install Java 8
2. Install Eclipse for [RCP and RAP developers](https://www.eclipse.org/downloads/packages/release/2018-12/r/eclipse-ide-rcp-and-rap-developers)
3. Configure Java 8 inside Eclipse Window > Preferences > Java > Installed JREs
4. Import this repo as an Existing Maven project
5. Activate target platform by going to Window > Preferences > Plug-in Development > Target Platform and check the `KNIME Analytics Platform (4.0) - rationalinsights-knime-plugin.targetplatform/KNIME-AP-4.0.target` target definition.

During import the Tycho Eclipse providers must be installed.

## Tests

Tests for the node are in `tests/src` directory.
Tests can be executed with `mvn verify`, they will be run in a separate KNIME environment.
Test results will be written to `test/target/surefire-reports` directory.
Code coverage reports (html+xml) can be found in the `tests/target/jacoco/report/` directory.

The tests can be run against a different KNIME version using `mvn verify -Dtarget.file=KNIME-AP-4.1` where `4.1` is the major.minor version of KNIME and `KNIME-AP-4.1` is a target platform definition file called `targetplatform/KNIME-AP-4.1.target`.

### Unit tests

Unit tests written in Junit4 format can be put in `tests/src/java`.

### Workflow tests

See https://github.com/3D-e-Chem/knime-testflow#3-add-test-workflow

The tests expect a `python3` executable in PATH with numpy, pandas and protobuf packages installed.

## Speed up builds

Running mvn commands can take a long time as Tycho fetches indices of all p2 update sites.
This can be skipped by running maven offline using `mvn -o`.

# New release

1. Update versions in pom files with `mvn org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=<version>-SNAPSHOT` command.
2. Create package with `mvn package`, will create update site in `p2/target/repository`
3. Run tests with `mvn verify`
4. Optionally, test node by installing it in KNIME from a local update site
5. Append new release to an update site
  1. Make clone of an update site repo
  2. Append release to the update site with `mvn install -Dtarget.update.site=<path to update site>`
6. Commit and push changes in this repo and update site repo.
7. Create a Github release
8. Update Zenodo entry
  1. Correct authors
  2. Correct license
9. Make nodes available to 3D-e-Chem KNIME feature by following steps at https://github.com/3D-e-Chem/knime-node-collection#new-release
10. Update `CITATION.cff` file with new DOI, version, release date

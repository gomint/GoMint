![GoMint](.github/gomint-github-optimized.png)

<h4 align="center">A new fresh Minecraft Bedrock Edition server<br>aiming for stability and performance</h4>
<p align="center">
  
  <!-- STAR BADGE -->
  <a href="https://github.com/GoMint/GoMint/stargazers">
    <img alt="GitHub Stars" src="https://img.shields.io/github/stars/GoMint/GoMint.svg">
  </a>
  <!-- ISSUES BADGE -->
  <a href="https://github.com/GoMint/GoMint/issues">
    <img alt="GitHub Stars" src="https://img.shields.io/github/issues/GoMint/GoMint.svg">
  </a>
  <!-- VERSION BADGE -->
  <a href="https://github.com/GoMint/GoMint">
    <img alt="GitHub Stars" src="https://img.shields.io/badge/version-1.0.5-green.svg">
  </a>
  <!-- LICENSE BADGE -->
  <a href="https://opensource.org/licenses/BSD-3-Clause">
    <img alt="License" src="https://img.shields.io/badge/License-BSD%203--Clause-blue.svg">
  </a>

</p>


Project
--
Build Version | Build Result
------------ | -------------|
Master Build | [![Build Status](https://travis-ci.org/GoMint/GoMint.svg?branch=master)](https://travis-ci.org/GoMint/GoMint)

GoMint is a Minecraft Bedrock Edition server software implementation that is still work in progress.
The goal is to provide a full, modifiable server with plugin support for Java.
The project is divided into four modules:

##### gomint-api
The API for plugin developers. This is currently not stable and will not be developed in this stage
of the project. It will be finalized once all core components inside the server work.

##### gomint-server
This is the implementation of the server. Fiddling with this is not supported but we will help you if we can.
This part of the project is developing the fastest. If you want to contribute to the software watch this module the
most.

##### gomint-native
Holds native code related to hashing and zlib.

##### gomint-converter
A standalone application that allows you to easily convert an Anvil world to LevelDB from the command line.

##### gomint-asset-compiler
Holds most of the data related to entities, blocks and more. The data is gathered via MiTM and from other sources

##### gomint-test-plugin
A plugin to test and see the API design. 

Deprecation Warnings
--
Currently, GoMint is in a fast development-mode. The API is in all corners not stable and may change over time. The goal is to develop an API with the implementation problems we face. We will break the API multiple times until we reach the first release. To keep the impact minimal we deprecate old methods and provide better alternatives you can use.

```diff
- Deprecated symbols (packages, fields, methods, classes etc.) will be deleted after 2 weeks of deprecation
```

## Contact & Social
[![Join the Discord](http://puu.sh/v9UB9/944431c790.png)](https://discord.gg/qC4nJVN)
[![Twitter](http://puu.sh/v9V9H/ad70c8acf7.png)](https://twitter.com/GomintPe)

## Todo
Join Discord for more information about the current tasks we do

## License

The code found in this repository is licensed under a 3-clause BSD license. See the LICENSE file for further
details.

## Documentation

There currently is no documentation as things are still changing frequently.

## Download

You can recieve the latest builds here: http://ci.gomint.io/job/GoMint/job/master/

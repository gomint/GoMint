![GoMint](.github/ASSETS/logo_optimized.png)

<h4 align="center">A new fresh Minecraft Bedrock Edition server<br>aiming for stability and performance</h4>
<p align="center">
  
  <!-- BUILD BADGE -->
  <a href="https://travis-ci.org/GoMint/GoMint">
    <img alt="Build Status" src="https://travis-ci.org/GoMint/GoMint.svg?branch=master">
  </a>
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

GoMint is an open-source Minecraft Bedrock Edition server software implementation that is still work in progress. The goal is to provide a server implementation aiming for performance and stability with support for plugins. Purely written in Java.

### A word of warning
Currently, GoMint is in a fast development-mode. The API is in all corners not stable and may change over time. The goal is to develop an API with the implementation problems we face. We will break the API multiple times until we reach the first release. To keep the impact minimal we deprecate symbols and provide better alternatives you can use.

```diff
- Deprecated symbols (packages, fields, methods, classes etc.) will be deleted after two weeks of deprecation
```

## tl;dr
| JDK  | Documentation                        | Download                                                   |
| ---- | ------------------------------------ | ---------------------------------------------------------- |
| 1.8  | [Click here](https://docs.gomint.io) | [Latest build](http://ci.gomint.io/job/GoMint/job/master/) |

### Compilation
Compiling GoMint is actually pretty easy. We'll guide you through the compilation step by step and address troubleshooting.

**Prerequisites**<br>
For compiling GoMint, you will need some prerequisites:
- Git
- Maven
- JDK 1.8
 
**Compiling**<br>
This project's choice of build tool is Maven. To compile GoMint using Maven follow these steps:
- Open up a terminal
- Change the working directory to the cloned GoMint repository
- Type the following command: `mvn clean install` (You can append `-T 4C` if you've got a decent machine)
- Let it compile. This will take some time. Grab a drink and relax.

**Troubleshooting**<br>
_No compile troubleshooting available._

### License
This project's choice of license is **BSD 3-Clause**. You may find the license file in the project's root directory.

<br>
<p align="center">
  
  <!-- DISCORD -->
  <a href="https://discord.gg/qC4nJVN" width="32">
    <img alt="Discord Logo" src=".github/ASSETS/logo_discord.png">
  </a>
  <!-- TWITTER -->
  <a href="https://twitter.com/GomintPe" width="32">
    <img alt="Discord Logo" src=".github/ASSETS/twitter_discord.png">
  </a>

</p>
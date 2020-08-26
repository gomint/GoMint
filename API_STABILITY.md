<p align="center">
  
![GoMint](.github/ASSETS/gomint-banner.png)

</p>

Every API file has a stability rating from 0 to 5 in its header indicating how stable 
the contents are. Those ratings are explained in this file.

0 - Deprecated
===
  
This feature is known to be problematic, and changes are
planned.  Do not rely on it.  Use of the feature may cause warnings.  Backwards
compatibility should not be expected.

1 - Experimental
===

This feature was introduced recently, and may change
or be removed in future versions.  Please try it out and provide feedback.
If it addresses a use-case that is important to you, tell the node core team.

2 - Unstable
===
  
The API is in the process of settling, but has not yet had
sufficient real-world testing to be considered stable. Backwards-compatibility
will be maintained if reasonable.

3 - Stable
===
  
The API has proven satisfactory, but cleanup in the underlying
code may cause minor changes.  Backwards-compatibility is guaranteed.

4 - API Frozen
===

This API has been tested extensively in production and is
unlikely to ever have to change.

5 - Locked
===
 
Unless serious bugs are found or mojang changes the underlying system, this code will not ever
change.  Please do not suggest changes in this area, they will be refused.

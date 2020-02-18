// Copyright (c) 2020 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
//
// WSO2 Inc. licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except
// in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

@typeParam
type PureType3 anydata | error;

# Takes in a lambda function and returns a new stream out of it.
#
# + td - A type description.
# + func - A lambda function.
# + return - New stream containing results of `func` invocation.
public function construct(typedesc<PureType3> td, function() returns PureType3 func) returns stream<PureType3> = external;

# Takes in a stream and returns the value gen function of that stream.
#
# + strm - The stream
# + return - A function pointer to the value gen function.
public function getGenFunc(stream<PureType3> strm) returns (function() returns record {| PureType3 value; |}?) = external;

// Copyright (c) 2019 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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


# Configuration related to a streaming connection.
#
# + ackTimeout - Timeout in seconds to wait for an acknowledgement for the corresponding subscription.
#                Default value is `30`.
# + connectionTimeout - Timeout in seconds to wait for a connection. Default value is `5`.
# + maxPubAcksInFlight - The maximum number of publish ACKs that may be in flight at any point in time.
#                        Default value is 16384.
# + discoverPrefix - Subject prefix used for server discovery. Default value is `_STAN.discover`
public type StreamingConfig record {|
    int ackTimeout = 30;
    int connectionTimeout = 5;
    int maxPubAcksInFlight = 16384;
    string discoverPrefix = "_STAN.discover";
|};
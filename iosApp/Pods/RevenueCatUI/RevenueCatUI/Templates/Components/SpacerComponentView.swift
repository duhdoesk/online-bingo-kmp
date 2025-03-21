//
//  Copyright RevenueCat Inc. All Rights Reserved.
//
//  Licensed under the MIT License (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//      https://opensource.org/licenses/MIT
//
//  SpacerComponentView.swift
//
//  Created by James Borthwick on 2024-08-19.

import Foundation
import RevenueCat
import SwiftUI

#if PAYWALL_COMPONENTS

@available(iOS 15.0, macOS 12.0, tvOS 15.0, watchOS 8.0, *)
struct SpacerComponentView: View {

    let locale: Locale
    let component: PaywallComponent.SpacerComponent

    var body: some View {
        Spacer()
    }

}

#endif

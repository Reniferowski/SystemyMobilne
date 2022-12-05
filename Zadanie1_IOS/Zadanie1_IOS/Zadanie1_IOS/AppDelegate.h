//
//  AppDelegate.h
//  Zadanie1_IOS
//
//  Created by student on 05/12/2022.
//  Copyright © 2022 example. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CoreData/CoreData.h>

@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (readonly, strong) NSPersistentContainer *persistentContainer;

- (void)saveContext;


@end

